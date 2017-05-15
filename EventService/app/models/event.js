var mongoose     = require('mongoose');
var Schema       = mongoose.Schema;

var EventSchema   = new Schema({
    id: String
});

module.exports = mongoose.model('Event', EventSchema);