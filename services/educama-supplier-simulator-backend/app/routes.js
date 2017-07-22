"use strict";
var express    = require('express');        // Call express
var Promise = require('bluebird');

// Models
var AirlineBooking = require('./models/airlineBooking');
var HaulierBooking = require('./models/haulierBooking');

module.exports = function(app) {
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
}