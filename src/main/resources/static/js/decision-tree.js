$(document).ready(function () {
    console.log('ready');
    $('.predict').click(function () {
        $.ajax({
            url: "predict?rating=" + true + "&modelAge=" + true + "&firstFlight=" + true + "&pilotAge=" + false + "&weather=" + false + "&crashed=" + false,
            type: "GET",
            success: function (result) {
                console.log(result['crashed'])
            },
        })
    })
});