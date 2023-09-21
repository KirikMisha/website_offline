document.addEventListener('DOMContentLoaded', function() {
    var firstNameInput = document.getElementById("firstName");
    var lastNameInput = document.getElementById("lastName");
    var resultsContainer = document.getElementById("results"); // Получаем контейнер результатов

    firstNameInput.addEventListener("keyup", search);
    lastNameInput.addEventListener("keyup", search);

    function search() {
        var firstName = firstNameInput.value;
        var lastName = lastNameInput.value;

        if (firstName.trim() !== '' || lastName.trim() !== '') {
            // Если хотя бы одно из полей поиска содержит символы, отобразите результаты
            resultsContainer.style.display = 'block'; // Отображаем контейнер результатов
        } else {
            // В противном случае скройте результаты
            resultsContainer.style.display = 'none'; // Скрываем контейнер результатов
        }

        axios.get("/list/search", {
            params: {
                firstName: firstName,
                lastName: lastName
            }
        })
            .then(function (response) {
                var results = response.data;
                var resultsContainer = document.getElementById("results");
                resultsContainer.innerHTML = "";

                for (var i = 0; i < results.length; i++) {
                    var person = results[i];
                    var card = createCard(person);
                    resultsContainer.appendChild(card);
                }
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    function createCard(person) {
        var card = document.createElement("div");
        card.classList.add("card", "mt-2");

        var img = document.createElement("img");
        img.src = "/icon/Icon_Person.png";
        img.classList.add("card-img-top");
        img.alt = "Изображение работника";
        card.appendChild(img);

        var cardBody = document.createElement("div");
        cardBody.classList.add("card-body");
        card.appendChild(cardBody);

        var title = document.createElement("h5");
        title.classList.add("card-title");
        title.textContent = person.firstName + " " + person.middleName + " " + person.lastName;
        cardBody.appendChild(title);

        var details = document.createElement("p");
        cardBody.appendChild(details);

        var position = document.createElement("span");
        position.classList.add("card-text");
        position.innerHTML = "<strong>Должность:</strong> " + person.position; // Добавляем <strong> вокруг "Должность"
        details.appendChild(position);
        details.appendChild(document.createElement("br"));

        var landlinePhone = document.createElement("span");
        landlinePhone.classList.add("card-text");
        landlinePhone.innerHTML = "<strong>Местный номер:</strong> " + person.landlinePhone; // Добавляем <strong> вокруг "Местный номер"
        details.appendChild(landlinePhone);
        details.appendChild(document.createElement("br"));

        var phoneNumber = document.createElement("span");
        phoneNumber.classList.add("card-text");
        phoneNumber.innerHTML = "<strong>Городской номер:</strong> " + person.phoneNumber; // Добавляем <strong> вокруг "Городской номер"
        details.appendChild(phoneNumber);
        details.appendChild(document.createElement("br"));

        var officeNumber = document.createElement("span");
        officeNumber.classList.add("card-text");
        officeNumber.innerHTML = "<strong>Номер здания:</strong> " + person.officeNumber; // Добавляем <strong> вокруг "Номер здания"
        details.appendChild(officeNumber);
        details.appendChild(document.createElement("br"));

        return card;
    }
});