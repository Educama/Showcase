var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var AirlineBookingSchema = new Schema({
    trackingNumber: String,
    airline: String,
    flightNumber: String,
    origin: String,
    destination: String,
    departedTime: Date,
    arrivedTime: Date,
    status: String
});

module.exports = mongoose.model('AirlineBooking', AirlineBookingSchema);