document.addEventListener('DOMContentLoaded', function() {
    var path = window.location.pathname;
    var links = document.querySelectorAll('header a');
    links.forEach(function(link) {
        if (link.getAttribute('href') === path) {
            link.classList.add('active');
        }
    });

    var navbarToggler = document.querySelector('.navbar-toggler');
    var collapsedNav = document.querySelector('#collapsedNav');

    navbarToggler.addEventListener('click', function() {
        collapsedNav.classList.toggle('show');
    });

    document.addEventListener('click', function(event) {
        var target = event.target;
        if (!target.closest('.navbar-toggler') && !target.closest('#collapsedNav')) {
            collapsedNav.classList.remove('show');
        }
    });

    var showVacationButton = document.getElementById('show-vacation-button');
    var vacationSubmenu = document.getElementById('vacation-submenu');

    showVacationButton.addEventListener('click', function() {
        // Переключите отображение подсписка "Отпуск" при нажатии на кнопку "Шаблоны"
        vacationSubmenu.style.display = (vacationSubmenu.style.display === 'block') ? 'none' : 'block';
    });
});
