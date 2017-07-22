var express    = require('express');        // Call express
var router = express.Router();              // get an instance of the express Router
var Promise = require('bluebird');

// Models
var AirlineBooking = require('../models/airlineBooking');
var HaulierBooking = require('../models/haulierBooking');

router.route('/')
    // Get all events(accessed at GET http://localhost:8080/api/bookings)
    .get(function(req, res) {
        Promise.props({
            airlineBookingEvents: AirlineBooking.find().execAsync(),
            haulierBookingEvents: HaulierBooking.find().execAsync()
       
         })
        .then(function(results) {
            res.json(results);
         })
        .catch(function(err) {
            res.send(err);
        });
    });

module.exports = router