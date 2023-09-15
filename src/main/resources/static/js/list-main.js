// Ждем, пока документ будет полностью загружен
document.addEventListener('DOMContentLoaded', function() {
    // Получите элементы поля поиска
    const firstNameInput = document.getElementById('firstName');
    const lastNameInput = document.getElementById('lastName');
    const resultsContainer = document.getElementById('results');

    // Слушайте событие input на полях поиска
    firstNameInput.addEventListener('input', toggleResults);
    lastNameInput.addEventListener('input', toggleResults);

    function toggleResults() {
        // Если хотя бы одно из полей поиска содержит символы, отобразите результаты
        if (firstNameInput.value.trim() !== '' || lastNameInput.value.trim() !== '') {
            resultsContainer.style.display = 'block'; // Отобразить результаты
        } else {
            resultsContainer.style.display = 'none'; // Скрыть результаты
        }
    }
});
