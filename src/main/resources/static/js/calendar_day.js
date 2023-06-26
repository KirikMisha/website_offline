document.addEventListener("DOMContentLoaded", function() {
    var currentDate = new Date();
    var formattedDate = currentDate.toLocaleDateString('ru-RU', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric' });

    document.getElementById('currentDayOfWeek').textContent = formattedDate;
});

//menu burger
$(document).ready(function () {
    $('.navbar-toggler').click(function () {
        $('.navbar-collapse').collapse('toggle');
    });
});