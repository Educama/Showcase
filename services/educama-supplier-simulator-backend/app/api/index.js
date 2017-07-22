var express    = require('express');        // Call express
var router = express.Router();              // get an instance of the express Router

router.use('/bookings', require('./bookings'))
router.use('/flights', require('./flights'))
router.use('/cargos', require('./cargos'))

// Middleware to use for all requests
router.use(function(req, res, next) {
    console.log('A request was sent');
    next(); // Make sure we go to the next routes and do not stop here
});

// Test route to make sure everything is working (accessed at GET http://localhost:8080/api)
router.get('/', function(req, res) {
    res.json({ message: 'Connection to the API was correct!' });   
});

module.exports = router