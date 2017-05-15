// BASE SETUP
// =============================================================================
var express    = require('express');        // call express
var app        = express();                 // define our app using express
var bodyParser = require('body-parser');
var cfenv = require("cfenv");
var appEnv = cfenv.getAppEnv();
var cors = require('cors');

// AMQP -- RabbitMQ
var amqp = require('amqplib/callback_api');
var amqpURLService = "amqp://jvunsmmp:4kq4FLfj77caAqsJbMtAeccZC6qJgaI5@lark.rmq.cloudamqp.com/jvunsmmp";
var amqpURLPivotal = appEnv.getServiceURL('MyQueue');

if(amqpURLPivotal === null) {     // Local or not found
    var amqpURL = amqpURLService;
}else{                            // Service found in Pivotal
    var amqpURL = amqpURLPivotal;
}

// MongoDB -- ScaleGrid
var fs = require('fs');
var mongoose   = require('mongoose');
var certFileBuf = fs.readFileSync("cert.csr");
var options = {
  server: { sslCA: certFileBuf }
};
var mongoURLService = "mongodb://admin:8BcpUzy2mAtXUNtv@SG-NovaTecCluster-10062.servers.mongodirector.com:27017/admin?ssl=true";
var mongoURLPivotal = appEnv.getServiceURL('MyMongo');

if(mongoURLPivotal === null) {      // Local or not found
    var mongoURL = mongoURLService;
}else{                              // Service found in Pivotal
    var mongoURL = mongoURLPivotal;
}

mongoose.connect(mongoURL, options, function(err, db) {
    if(err){
        return console.dir(err);
    }
});

// Models
var Event = require('./app/models/event');

// App configuration
app.use(cors());
app.use(bodyParser.urlencoded({ extended: true })); // It will let us get the data from a POST
app.use(bodyParser.json());
app.use('/', express.static(__dirname + '/www')); // redirect root
app.use('/js', express.static(__dirname + '/node_modules/bootstrap/dist/js')); // redirect bootstrap JS
app.use('/js', express.static(__dirname + '/node_modules/jquery/dist')); // redirect JS jQuery
app.use('/css', express.static(__dirname + '/node_modules/bootstrap/dist/css')); // redirect CSS bootstrap

app.set( 'views', __dirname + '/app/views');
app.set( 'view engine', 'jade');

app.get( '/', function ( req, res) {
  res.render( 'index');
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
router.route('/events')

    // Create an event (accessed at POST http://localhost:8080/api/events)
    .post(function(req, res) {
        
        var event = new Event();      // create a new instance of the Event model
        event.id = req.body.id;       // set the event id (comes from the request)

        // Save the event and check for errors
        event.save(function(err) {
            if (err){
                res.send(err);
            }else{
                res.json({ message: 'Event created' });
                
                // Connect to RabbitMQ server
                amqp.connect(amqpURL, function(err, conn) {

                  // Create a channel
                  conn.createChannel(function(err, ch) {
                    var q = 'Queue';
                    var msg = JSON.stringify(event);

                    // Declare a queue for us to send to; then we can publish a message to the queue
                    ch.assertQueue(q, {durable: false});
                    ch.sendToQueue(q, new Buffer(msg));
                  });
                  
                });
            }
        });
    })
    
    // Get all the events (accessed at GET http://localhost:8080/api/events)
    .get(function(req, res) {
        Event.find(function(err, events) {
            if (err) {
                res.send(err);
            }else{
                res.json(events);
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