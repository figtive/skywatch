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
                let finalResult = result['crashed'];
                if (finalResult) {
                    $('#crashedModal').modal('show');
                } else if (!finalResult){
                    $('#notCrashedModal').modal('show');
                }
                console.log(result['crashed'])
            }
        })
    })
});