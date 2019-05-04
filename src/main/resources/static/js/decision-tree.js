$(document).ready(function () {
    console.log('ready');
    $('#submit-button').click(function () {
        console.log("it works");
        let urlVar = "predict?rating=" + $(".ratingRadio:checked").val() +
            "&modelAge=" + $(".modelAgeRadio:checked").val() +
            "&firstFlight=" + $(".firstFlightRadio:checked").val() +
            "&pilotAge=" + $(".pilotAgeRadio:checked").val() +
            "&weather=" + $(".weatherRadio:checked").val();
        // console.log(url);
        $.ajax({
            url: urlVar,
            type: "GET",
            success: function (result) {
                let finalresult = result['crashed'];
                if (finalresult == true) {

                }
                console.log(result['crashed'])
            }
        })
    })
});