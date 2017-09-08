/* global __dirname */

// BASE SETUP
// =============================================================================
var express    = require('express');        // Call express
var app        = express();                 // Define our app using express
var bodyParser = require('body-parser');
var cfenv = require("cfenv");
var appEnv = cfenv.getAppEnv();
var cors = require('cors');
var Promise = require('bluebird');

// AMQP -- RabbitMQ
var amqp = require('amqplib/callback_api');
var amqpURLService = "amqp://jvunsmmp:4kq4FLfj77caAqsJbMtAeccZC6qJgaI5@lark.rmq.cloudamqp.com/jvunsmmp";
var amqpURLBluemix = appEnv.getServiceURL('supplier-simulator-queue');

if(amqpURLBluemix === null) {     // Local or not found
    var amqpURL = amqpURLService;
}else{                            // Service found in Bluemix
    var amqpURL = amqpURLBluemix;
}

// MongoDB -- ScaleGrid
var mongoose   = require('mongoose');
Promise.promisifyAll(mongoose);
var fs = require('fs');
var assert = require('assert');
var mongoURL;
var mongoURLService = "mongodb://EducamaUser:NovaTec@ds151141.mlab.com:51141/suppliersimulatordatabase";
var mongoURLBluemix = appEnv.getServiceURL("supplier-simulator-database");
var certFileBuf = fs.readFileSync("cert.csr");
var mongoDbOptions = {
   server: { sslCA: certFileBuf }
};
    
if(mongoURLBluemix === null) {         // Local or not found 
    mongoURL = mongoURLService;
}else{                                 // Service found in Bluemix
    mongoURL = mongoURLBluemix;
}

mongoose.connect(mongoURL, mongoDbOptions, function(err, db) {
    if(err){
        return console.dir(err);
    }
});

// Models
var AirlineBooking = require('./app/models/airlineBooking');
var HaulierBooking = require('./app/models/haulierBooking');

// App configuration
app.use(cors());
app.use(bodyParser.urlencoded({ extended: true })); // It will let us get the data from a POST
app.use(bodyParser.json());
app.use('/', express.static(__dirname + '/www')); // redirect root
app.use('/js', express.static(__dirname + '/node_modules/bootstrap/dist/js')); // Redirect bootstrap JS
app.use('/js', express.static(__dirname + '/node_modules/jquery/dist')); // Redirect JS jQuery
app.use('/css', express.static(__dirname + '/node_modules/bootstrap/dist/css')); // Redirect CSS bootstrap
app.use('/js', express.static(__dirname + '/app/public/js')); // Redirect personal JS

app.set( 'views', __dirname + '/app/views');
app.set( 'view engine', 'jade');

app.get( '/', function ( req, res) {

    Promise.props({
    airlineBookingEvents: AirlineBooking.find().execAsync(),
    haulierBookingEvents: HaulierBooking.find().execAsync()
       
    })
    .then(function(results) {
        res.render('index', {
            airlineBookingEvents: results.airlineBookingEvents,
            haulierBookingEvents: results.haulierBookingEvents
        });
    })
    .catch(function(err) {
        res.send(500);
    });
});

var port = process.env.PORT || 8080;        // set our port

// ROUTES FOR OUR API
// =============================================================================
var router = express.Router();              // get an instance of the express Router

// Middleware to use for all requests
router.use(function(req, res, next) {
    console.log('A request was sent');
    next(); // Make sure we go to the next routes and do not stop here
});

// Test route to make sure everything is working (accessed at GET http://localhost:8080/api)
router.get('/', function(req, res) {
    res.json({ message: 'Connection to the API was correct!' });   
});

// ----------------------------------------------------
router.route('/flights')

    // Create an airline booking (accessed at POST http://localhost:8080/api/flights)
    .post(function(req, res) {
        
        var flight = new AirlineBooking();      // Create a new instance of the airlineBooking model
        flight.trackingNumber = req.body.trackingNumber;
        flight.airline = req.body.airline;
        flight.flightNumber = req.body.flightNumber;
        flight.origin = req.body.origin;
        flight.destination = req.body.destination;
        flight.status = "Requested";
        flight.departedTime = null;
        flight.arrivedTime = null;

        // Save the flight and check for errors
        flight.save(function(err) {
            if (err){
                res.send(err);
            }else{
                res.json({ message: 'Successfully created' });
                
                // Connect to RabbitMQ server
                amqp.connect(amqpURL, function(err, conn) {

                    // Create a channel
                    conn.createChannel(function(err, ch) {
                        var bookingResponseQueue = 'bookingResponseQueue';
                        var msg = JSON.stringify(flight);

                        // Declare a queue for us to send to; then we can publish a message to the queue
                        ch.assertQueue(bookingResponseQueue, {durable: false});
                        ch.sendToQueue(bookingResponseQueue, new Buffer(msg));
                    });
                  
                });
            }
        });
    })
    
    // Get all the airline booking events (accessed at GET http://localhost:8080/api/flights)
    .get(function(req, res) {
        AirlineBooking.find(function(err, flight) {
            if (err) {
                res.send(err);
            }else{
                res.json(flight);
            }
        });
    });
    
router.route('/flights/:flight_id')

    // Delete the airline booking event given the ID (accessed at DELETE http://localhost:8080/api/flights/:flight_id)
     .delete(function(req, res) {
        
          AirlineBooking.findById(req.params.flight_id, function(err, flight) {

            if (err) {
                res.send(err);
            } else {
                flight.status = "Declined";   

                flight.save(function(err) {
                    if (err){
                        res.send(err);
                    }else{
                        res.json({ message: 'Flight updated!' });
                    }
                });
                
                // Connect to RabbitMQ server
                amqp.connect(amqpURL, function(err, conn) {

                    // Create a channel
                    conn.createChannel(function(err, ch) {
                        var bookingResponseQueue = 'bookingResponseQueue';
                        var msg = JSON.stringify(flight);

                        // Declare a queue for us to send to; then we can publish a message to the queue
                        ch.assertQueue(bookingResponseQueue, {durable: false});
                        ch.sendToQueue(bookingResponseQueue, new Buffer(msg));
                    });
                  
                });
            }
        });
   })
        
     // Update the airline booking event given the ID (accessed at PUT http://localhost:8080/api/flights/:flight_id)
    .put(function(req, res) {
        
       AirlineBooking.findById(req.params.flight_id, function(err, flight) {

            if (err) {
                res.send(err);
            } else {
                flight.status = "Confirmed";   

                flight.save(function(err) {
                    if (err){
                        res.send(err);
                    }else{
                        res.json({ message: 'Flight updated!' });
                    }
                });
                
                // Connect to RabbitMQ server
                amqp.connect(amqpURL, function(err, conn) {

                    // Create a channel
                    conn.createChannel(function(err, ch) {
                        var bookingResponseQueue = 'bookingResponseQueue';
                        var msg = JSON.stringify(flight);

                        // Declare a queue for us to send to; then we can publish a message to the queue
                        ch.assertQueue(bookingResponseQueue, {durable: false});
                        ch.sendToQueue(bookingResponseQueue, new Buffer(msg));
                    });
                  
                });
            }
        });
       
    });
    
router.route('/flights/:flight_id/departed')

    // Delete the airline booking event given the ID (accessed at DELETE http://localhost:8080/api/flights/:flight_id/departed)
     .put(function(req, res) {
        
          AirlineBooking.findById(req.params.flight_id, function(err, flight) {

            if (err) {
                res.send(err);
            } else {
                flight.status = "Departed";
                flight.departedTime = new Date();

                flight.save(function(err) {
                    if (err){
                        res.send(err);
                    }else{
                        res.json({ message: 'Flight updated!' });
                    }
                });
                
                // Connect to RabbitMQ server
                amqp.connect(amqpURL, function(err, conn) {

                    // Create a channel
                    conn.createChannel(function(err, ch) {
                        var bookingResponseQueue = 'bookingResponseQueue';
                        var msg = JSON.stringify(flight);

                        // Declare a queue for us to send to; then we can publish a message to the queue
                        ch.assertQueue(bookingResponseQueue, {durable: false});
                        ch.sendToQueue(bookingResponseQueue, new Buffer(msg));
                    });
                  
                });
            }
        });
   });
   
router.route('/flights/:flight_id/arrived')

    // Delete the airline booking event given the ID (accessed at DELETE http://localhost:8080/api/flights/:flight_id/arrived)
     .put(function(req, res) {
        
          AirlineBooking.findById(req.params.flight_id, function(err, flight) {

            if (err) {
                res.send(err);
            } else {
                flight.status = "Arrived";
                flight.arrivedTime = new Date();

                flight.save(function(err) {
                    if (err){
                        res.send(err);
                    }else{
                        res.json({ message: 'Flight updated!' });
                    }
                });
                
                // Connect to RabbitMQ server
                amqp.connect(amqpURL, function(err, conn) {

                    // Create a channel
                    conn.createChannel(function(err, ch) {
                        var bookingResponseQueue = 'bookingResponseQueue';
                        var msg = JSON.stringify(flight);

                        // Declare a queue for us to send to; then we can publish a message to the queue
                        ch.assertQueue(bookingResponseQueue, {durable: false});
                        ch.sendToQueue(bookingResponseQueue, new Buffer(msg));
                    });
                  
                });
            }
        });
   });
   
   // ----------------------------------------------------
router.route('/cargos')

    // Create a haulier booking (accessed at POST http://localhost:8080/api/cargos)
    .post(function(req, res) {
        
        var cargo = new HaulierBooking();      // Create a new instance of the haulierBooking model
        cargo.trackingNumber = req.body.trackingNumber;
        cargo.supplierID = req.body.supplierID;
        cargo.origin = req.body.origin;
        cargo.destination = req.body.destination;
        cargo.status = "Requested";
        cargo.pickedUpTime = null;
        cargo.deliveredTime = null;

        // Save the cargo and check for errors
        cargo.save(function(err) {
            if (err){
                res.send(err);
            }else{
                res.json({ message: 'Successfully created' });
                
                // Connect to RabbitMQ server
                amqp.connect(amqpURL, function(err, conn) {

                    // Create a channel
                    conn.createChannel(function(err, ch) {
                        var bookingResponseQueue = 'bookingResponseQueue';
                        var msg = JSON.stringify(cargo);

                        // Declare a queue for us to send to; then we can publish a message to the queue
                        ch.assertQueue(bookingResponseQueue, {durable: false});
                        ch.sendToQueue(bookingResponseQueue, new Buffer(msg));
                    });
                  
                });
            }
        });
    })
    
    // Get all the haulier booking events (accessed at GET http://localhost:8080/api/cargos)
    .get(function(req, res) {
        HaulierBooking.find(function(err, cargo) {
            if (err) {
                res.send(err);
            }else{
                res.json(cargo);
            }
        });
    });
    
router.route('/cargos/:cargo_id')

    // Delete the haulier booking event given the ID (accessed at DELETE http://localhost:8080/api/cargos/:cargo_id)
     .delete(function(req, res) {
        
          HaulierBooking.findById(req.params.cargo_id, function(err, cargo) {

            if (err) {
                res.send(err);
            } else {
                cargo.status = "Declined";   

                cargo.save(function(err) {
                    if (err){
                        res.send(err);
                    }else{
                        res.json({ message: 'Cargo updated!' });
                    }
                });
                
                // Connect to RabbitMQ server
                amqp.connect(amqpURL, function(err, conn) {

                    // Create a channel
                    conn.createChannel(function(err, ch) {
                        var bookingResponseQueue = 'bookingResponseQueue';
                        var msg = JSON.stringify(cargo);

                        // Declare a queue for us to send to; then we can publish a message to the queue
                        ch.assertQueue(bookingResponseQueue, {durable: false});
                        ch.sendToQueue(bookingResponseQueue, new Buffer(msg));
                    });
                  
                });
            }
        });
   })
        
     // Update the haulier booking event given the ID (accessed at PUT http://localhost:8080/api/cargos/:cargo_id)
    .put(function(req, res) {
        
       HaulierBooking.findById(req.params.cargo_id, function(err, cargo) {

            if (err) {
                res.send(err);
            } else {
                cargo.status = "Confirmed";   

                cargo.save(function(err) {
                    if (err){
                        res.send(err);
                    }else{
                        res.json({ message: 'Cargo updated!' });
                    }
                });
                
                // Connect to RabbitMQ server
                amqp.connect(amqpURL, function(err, conn) {

                    // Create a channel
                    conn.createChannel(function(err, ch) {
                        var bookingResponseQueue = 'bookingResponseQueue';
                        var msg = JSON.stringify(cargo);

                        // Declare a queue for us to send to; then we can publish a message to the queue
                        ch.assertQueue(bookingResponseQueue, {durable: false});
                        ch.sendToQueue(bookingResponseQueue, new Buffer(msg));
                    });
                  
                });
            }
        });
       
    });
    
router.route('/cargos/:cargo_id/pickedup')

    // Delete the haulier booking event given the ID (accessed at DELETE http://localhost:8080/api/cargos/:cargo_id/departed)
     .put(function(req, res) {
        
          HaulierBooking.findById(req.params.cargo_id, function(err, cargo) {

            if (err) {
                res.send(err);
            } else {
                cargo.status = "PickedUp";
                cargo.pickedUpTime = new Date();

                cargo.save(function(err) {
                    if (err){
                        res.send(err);
                    }else{
                        res.json({ message: 'Cargo updated!' });
                    }
                });
                
                // Connect to RabbitMQ server
                amqp.connect(amqpURL, function(err, conn) {

                    // Create a channel
                    conn.createChannel(function(err, ch) {
                        var bookingResponseQueue = 'bookingResponseQueue';
                        var msg = JSON.stringify(cargo);

                        // Declare a queue for us to send to; then we can publish a message to the queue
                        ch.assertQueue(bookingResponseQueue, {durable: false});
                        ch.sendToQueue(bookingResponseQueue, new Buffer(msg));
                    });
                  
                });
            }
        });
   });
   
router.route('/cargos/:cargo_id/delivered')

    // Delete the haulier booking event given the ID (accessed at DELETE http://localhost:8080/api/cargos/:cargo_id/arrived)
     .put(function(req, res) {
        
          HaulierBooking.findById(req.params.cargo_id, function(err, cargo) {

            if (err) {
                res.send(err);
            } else {
                cargo.status = "Delivered";
                cargo.deliveredTime = new Date();

                cargo.save(function(err) {
                    if (err){
                        res.send(err);
                    }else{
                        res.json({ message: 'Cargo updated!' });
                    }
                });
                
                // Connect to RabbitMQ server
                amqp.connect(amqpURL, function(err, conn) {

                    // Create a channel
                    conn.createChannel(function(err, ch) {
                        var bookingResponseQueue = 'bookingResponseQueue';
                        var msg = JSON.stringify(cargo);

                        // Declare a queue for us to send to; then we can publish a message to the queue
                        ch.assertQueue(bookingResponseQueue, {durable: false});
                        ch.sendToQueue(bookingResponseQueue, new Buffer(msg));
                    });
                  
                });
            }
        });
   });
    
// REGISTER OUR ROUTES
// =============================================================================
// All of our routes will be prefixed with /api
app.use('/api', router);

// START THE SERVER
// =============================================================================
app.listen(port);
console.log('Listening on port ' + port);