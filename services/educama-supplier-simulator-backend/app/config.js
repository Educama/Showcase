"use strict";
var express    = require('express');        // Call express
var app        = express();                 // Define our app using express
var bodyParser = require('body-parser');
var cfenv = require("cfenv");
var appEnv = cfenv.getAppEnv();
var cors = require('cors');

module.exports = function(app) {
    app.set('port', process.env.PORT || 8080);
    app.set('ip', process.env.IP || "127.0.0.1");

    app.use(cors());
    app.use(bodyParser.urlencoded({ extended: true })); // It will let us get the data from a POST
    app.use(bodyParser.json());
    app.use('/', express.static(__dirname + '/www')); // redirect root
    app.use('/js', express.static(__dirname + '/node_modules/bootstrap/dist/js')); // Redirect bootstrap JS
    app.use('/js', express.static(__dirname + '/node_modules/jquery/dist')); // Redirect JS jQuery
    app.use('/css', express.static(__dirname + '/node_modules/bootstrap/dist/css')); // Redirect CSS bootstrap
    app.use('/js', express.static(__dirname + '/public/js')); // Redirect personal JS

    app.set( 'views', __dirname + '/views');
    app.set( 'view engine', 'jade');
    
    // Rabbit mq url
    var amqpURLService = "amqp://jvunsmmp:4kq4FLfj77caAqsJbMtAeccZC6qJgaI5@lark.rmq.cloudamqp.com/jvunsmmp";
    var amqpURLBluemix = appEnv.getServiceURL('supplier-simulator-queue');

    if(amqpURLBluemix === null) {     // Local or not found
        var amqpURL = amqpURLService;
    }else{                            // Service found in Bluemix
        var amqpURL = amqpURLBluemix;
    }
    app.set('amqpURL', amqpURL);

    //Mongo DB url
    var mongoURL;
    var mongoURLService = "mongodb://EducamaUser:NovaTec@ds151141.mlab.com:51141/suppliersimulatordatabase";
    var mongoURLBluemix = appEnv.getServiceURL("supplier-simulator-database");
    if(mongoURLBluemix === null) {         // Local or not found 
        mongoURL = mongoURLService;
    }else{                                 // Service found in Bluemix
        mongoURL = mongoURLBluemix;
    }
    app.set('mongoURL', mongoURL);
};