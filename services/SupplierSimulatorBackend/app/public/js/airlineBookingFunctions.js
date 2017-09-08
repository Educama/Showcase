function confirmAirlineBooking(id){

    $.ajax({
        type: "PUT",
        url: "/api/flights/"+id,
        contentType:'application/json',
        success: function(result) {
            location.reload();
        }
    });
    
}

function declineAirlineBooking(id){

    $.ajax({
        type: "DELETE",
        url: "/api/flights/"+id,
        contentType:'application/json',
        success: function(result) {
            location.reload();
        }
    });
    
}

function departedFlight(id){

    $.ajax({
        type: "PUT",
        url: "/api/flights/"+id+"/departed",
        contentType:'application/json',
        success: function(result) {
            location.reload();
        }
    });
    
}

function arrivedFlight(id){

    $.ajax({
        type: "PUT",
        url: "/api/flights/"+id+"/arrived",
        contentType:'application/json',
        success: function(result) {
            location.reload();
        }
    });
    
}

