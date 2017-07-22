var express    = require('express');        // Call express
var router = express.Router();              // get an instance of the express Router
var amqp = require('amqplib/callback_api'); // AMQP -- RabbitMQ

// Models
var HaulierBooking = require('../models/haulierBooking');
var message = require('../models/message');

router.route('/')

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
                amqp.connect(req.app.get('amqpURL'), function(err, conn) {

                    // Create a channel
                    conn.createChannel(function(err, ch) {
                        var bookingResponseQueue = 'bookingResponseQueue';
                        var msg = JSON.stringify(message.getMessageFromHaulierBooking(cargo));

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
    
router.route('/:cargo_id')

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
                amqp.connect(req.app.get('amqpURL'), function(err, conn) {

                    // Create a channel
                    conn.createChannel(function(err, ch) {
                        var bookingResponseQueue = 'bookingResponseQueue';
                        var msg = JSON.stringify(message.getMessageFromHaulierBooking(cargo));

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
                amqp.connect(req.app.get('amqpURL'), function(err, conn) {

                    // Create a channel
                    conn.createChannel(function(err, ch) {
                        var bookingResponseQueue = 'bookingResponseQueue';
                        var msg = JSON.stringify(message.getMessageFromHaulierBooking(cargo));

                        // Declare a queue for us to send to; then we can publish a message to the queue
                        ch.assertQueue(bookingResponseQueue, {durable: false});
                        ch.sendToQueue(bookingResponseQueue, new Buffer(msg));
                    });
                  
                });
            }
        });
       
    });
    
router.route('/:cargo_id/pickedup')

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
                amqp.connect(req.app.get('amqpURL'), function(err, conn) {

                    // Create a channel
                    conn.createChannel(function(err, ch) {
                        var bookingResponseQueue = 'bookingResponseQueue';
                        var msg = JSON.stringify(message.getMessageFromHaulierBooking(cargo));

                        // Declare a queue for us to send to; then we can publish a message to the queue
                        ch.assertQueue(bookingResponseQueue, {durable: false});
                        ch.sendToQueue(bookingResponseQueue, new Buffer(msg));
                    });
                  
                });
            }
        });
   });
   
router.route('/:cargo_id/delivered')

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
                amqp.connect(req.app.get('amqpURL'), function(err, conn) {

                    // Create a channel
                    conn.createChannel(function(err, ch) {
                        var bookingResponseQueue = 'bookingResponseQueue';
                        var msg = JSON.stringify(message.getMessageFromHaulierBooking(cargo));

                        // Declare a queue for us to send to; then we can publish a message to the queue
                        ch.assertQueue(bookingResponseQueue, {durable: false});
                        ch.sendToQueue(bookingResponseQueue, new Buffer(msg));
                    });
                  
                });
            }
        });
   });

module.exports = router