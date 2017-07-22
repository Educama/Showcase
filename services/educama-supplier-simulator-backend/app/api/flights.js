var express    = require('express');        // Call express
var router = express.Router();              // get an instance of the express Router
var amqp = require('amqplib/callback_api'); // AMQP -- RabbitMQ

// Models
var AirlineBooking = require('../models/airlineBooking');
var message = require('../models/message');

router.route('/')

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
                amqp.connect(req.app.get('amqpURL'), function(err, conn) {

                    // Create a channel
                    conn.createChannel(function(err, ch) {
                        var bookingResponseQueue = 'bookingResponseQueue';
                        var msg = JSON.stringify(message.getMessageFromAirlineBooking(flight));

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
    
router.route('/:flight_id')

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
                amqp.connect(req.app.get('amqpURL'), function(err, conn) {

                    // Create a channel
                    conn.createChannel(function(err, ch) {
                        var bookingResponseQueue = 'bookingResponseQueue';
                        var msg = JSON.stringify(message.getMessageFromAirlineBooking(flight));

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
                amqp.connect(req.app.get('amqpURL'), function(err, conn) {

                    // Create a channel
                    conn.createChannel(function(err, ch) {
                        var bookingResponseQueue = 'bookingResponseQueue';
                        var msg = JSON.stringify(message.getMessageFromAirlineBooking(flight));

                        // Declare a queue for us to send to; then we can publish a message to the queue
                        ch.assertQueue(bookingResponseQueue, {durable: false});
                        ch.sendToQueue(bookingResponseQueue, new Buffer(msg));
                    });
                  
                });
            }
        });
       
    });
    
router.route('/:flight_id/departed')

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
                amqp.connect(req.app.get('amqpURL'), function(err, conn) {

                    // Create a channel
                    conn.createChannel(function(err, ch) {
                        var bookingResponseQueue = 'bookingResponseQueue';
                        var msg = JSON.stringify(message.getMessageFromAirlineBooking(flight));

                        // Declare a queue for us to send to; then we can publish a message to the queue
                        ch.assertQueue(bookingResponseQueue, {durable: false});
                        ch.sendToQueue(bookingResponseQueue, new Buffer(msg));
                    });
                  
                });
            }
        });
   });
   
router.route('/:flight_id/arrived')

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
                amqp.connect(req.app.get('amqpURL'), function(err, conn) {

                    // Create a channel
                    conn.createChannel(function(err, ch) {
                        var bookingResponseQueue = 'bookingResponseQueue';
                        var msg = JSON.stringify(message.getMessageFromAirlineBooking(flight));

                        // Declare a queue for us to send to; then we can publish a message to the queue
                        ch.assertQueue(bookingResponseQueue, {durable: false});
                        ch.sendToQueue(bookingResponseQueue, new Buffer(msg));
                    });
                  
                });
            }
        });
   });

module.exports = router
   