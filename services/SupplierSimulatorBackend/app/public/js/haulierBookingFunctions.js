function confirmHaulierBooking(id){

    $.ajax({
        type: "PUT",
        url: "/api/cargos/"+id,
        contentType:'application/json',
        success: function(result) {
            location.reload();
        }
    });
    
}

function declineHaulierBooking(id){

    $.ajax({
        type: "DELETE",
        url: "/api/cargos/"+id,
        contentType:'application/json',
        success: function(result) {
            location.reload();
        }
    });
    
}

function pickedUpCargo(id){

    $.ajax({
        type: "PUT",
        url: "/api/cargos/"+id+"/pickedup",
        contentType:'application/json',
        success: function(result) {
            location.reload();
        }
    });
    
}

function deliveredCargo(id){

    $.ajax({
        type: "PUT",
        url: "/api/cargos/"+id+"/delivered",
        contentType:'application/json',
        success: function(result) {
            location.reload();
        }
    });
    
}

