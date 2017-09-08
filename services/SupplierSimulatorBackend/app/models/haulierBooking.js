var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var HaulierBookingSchema = new Schema({
    trackingNumber: String,
    supplierID: String,
    origin: String,
    destination: String,
    status: String,
    pickedUpTime: Date,
    deliveredTime: Date
});

module.exports = mongoose.model('HaulierBooking', HaulierBookingSchema);