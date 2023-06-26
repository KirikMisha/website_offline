document.addEventListener('DOMContentLoaded', function() {
    var firstNameInput = document.getElementById("firstName");
    var lastNameInput = document.getElementById("lastName");

    firstNameInput.addEventListener("keyup", search);
    lastNameInput.addEventListener("keyup", search);

    function search() {
        var firstName = firstNameInput.value;
        var lastName = lastNameInput.value;

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

        var phoneNumber = document.createElement("span");
        phoneNumber.classList.add("card-text");
        phoneNumber.textContent = "Городской номер: " + person.phoneNumber;
        details.appendChild(phoneNumber);
        details.appendChild(document.createElement("br"));

        var landlinePhone = document.createElement("span");
        landlinePhone.classList.add("card-text");
        landlinePhone.textContent = "Местный номер: " + person.landlinePhone;
        details.appendChild(landlinePhone);
        details.appendChild(document.createElement("br"));

        var position = document.createElement("span");
        position.classList.add("card-text");
        position.textContent = "Должность: " + person.position;
        details.appendChild(position);
        details.appendChild(document.createElement("br"));

        var officeNumber = document.createElement("span");
        officeNumber.classList.add("card-text");
        officeNumber.textContent = "Номер здания: " + person.officeNumber;
        details.appendChild(officeNumber);
        details.appendChild(document.createElement("br"));

        return card;
    }
});