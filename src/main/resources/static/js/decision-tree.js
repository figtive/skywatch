$(document).ready(function () {
    console.log('ready');
    $('#submit-button').click(function () {
        $.ajax({
            url: "predict?rating=" + $(".ratingRadio:checked").val() +
            "&modelAge=" + $(".modelAgeRadio:checked").val() + 
            "&firstFlight=" + $(".firstFlightRadio:checked").val() +
            "&pilotAge=" + $(".pilotAgeRadio:checked").val() +
            "&weather=" + $(".weatherRadio:checked").val(),
            type: "GET",
            success: function (result) {
                console.log(result['crashed'])
            }
        })
    })
});