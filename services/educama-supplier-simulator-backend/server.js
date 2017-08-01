/* global __dirname */

// BASE SETUP
// =============================================================================
var express    = require('express');        // Call express
var app        = express();                 // Define our app using express
var Promise = require('bluebird');

var config = require('./app/config');
var routes = require('./app/routes');

config(app);
routes(app);

// MongoDB -- ScaleGrid
var mongoose   = require('mongoose');
Promise.promisifyAll(mongoose);
var fs = require('fs');

var certFileBuf = fs.readFileSync("cert.csr");
var mongoDbOptions = {
   server: { sslCA: certFileBuf }
};

mongoose.connect(app.get('mongoURL'), mongoDbOptions, function(err, db) {
    if(err){
        return console.dir(err);
    }
});


// REGISTER OUR ROUTES FOR OUR API
// =============================================================================

app.use('/api', require('./app/api'));

// START THE SERVER
// =============================================================================
var server = app.listen(app.get('port'), app.get('ip'), function() {
    console.log('Express server listening on ' + app.get('ip') + ':' + app.get('port'));
});

module.exports = server;