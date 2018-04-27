$(document).ready(function () {


    $('#notifications').click(function () {
        return false;       // DO NOTHING WHEN CONTAINER IS CLICKED.
    });
    $("#notificationLink").click(function ()
    {
        $("#notificationContainer").fadeToggle(300);
        $("#notification_count").fadeOut("slow");
        return false;
    });

    //Document Click hiding the popup 
    $(document).click(function ()
    {
        $("#notificationContainer").hide();
    });

    //Popup on click
    $("#notificationContainer").click(function ()
    {
        return false;
    });
//    $(".datetimepicker").datetimepicker();
    


//                $('#datetimepicker').datetimepicker({
//	icons: {
//		time: "fa fa-clock-o",
//		date: "fa fa-calendar",
//		up: "fa fa-chevron-up",
//		down: "fa fa-chevron-down",
//		previous: 'fa fa-chevron-left',
//		next: 'fa fa-chevron-right',
//		today: 'fa fa-screenshot',
//		clear: 'fa fa-trash',
//		close: 'fa fa-remove'
//	}
});
