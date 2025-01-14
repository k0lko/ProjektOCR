document.addEventListener("DOMContentLoaded", function () {

    fetch("http://localhost:8081/api/categories")
        .then((response) => {
            // If the response is not 2xx, throw an error
            if (!response.ok) {
                throw new Error("Napotkano na błąd podczas odczytu dostępnych kategorii");
            }

            // If the response is 200 OK, return the response in JSON format.
            return response.json();
        })
        .then((data) => {
            const categoryList = document.getElementById("category")
            data.forEach(category => {
                // allCategories.push(category.name);
                const optionElement = document.createElement("option")
                optionElement.value = category.name
                optionElement.textContent = category.name
                categoryList.appendChild(optionElement);
                const liElement = document.createElement("li")
                const filterDropdownList = document.getElementById("filterDropdownList")
                liElement.textContent = category.name;
                liElement.addEventListener("click", function() {
                    liElement.classList.toggle("category-checked")
                    const checkedCategory = liElement.textContent;
                    const categoryLinks = document.getElementById("categoryLinks");
                    Array.from(categoryLinks.children).forEach((child) => {
                        console.log(child.textContent)
                        console.log(checkedCategory)
                        if (!child.textContent.includes(checkedCategory)) {
                            if (liElement.classList.contains("category-checked")) {
                                child.classList.add("hidden")
                            } else {
                                child.classList.remove("hidden")
                            }
                        }
                    });
                })
                filterDropdownList.appendChild(liElement)
            });
        })
        .catch((error) =>  alert("Napotkano na błąd podczas odczytu dostępnych kategorii"))

    fetch("http://localhost:8081/api/notes")
        .then((response) => {
            // If the response is not 2xx, throw an error
            if (!response.ok) {
                throw new Error("Napotkano na błąd podczas odczytu dostępnych notatek");
            }

            // If the response is 200 OK, return the response in JSON format.
            return response.json();
        })
        .then((data) => {
            const categoryLinks = document.getElementById('categoryLinks');
            data.forEach(note => {
                const liElement = document.createElement("li")
                liElement.textContent = `${note.title} (${note.category.name})`;
                categoryLinks.appendChild(liElement);
            });
        })
        .catch((error) =>  alert("Napotkano na błąd podczas odczytu dostępnych notatek"))
});

// Handle adding a new category
document.getElementById('addCategory').addEventListener('click', () => {
    const newCategory = prompt('Wprowadź nową kategorię:');
    if (newCategory) {
        const categoryList = document.getElementById("category")
        const optionElement = document.createElement("option")
        optionElement.value = newCategory
        optionElement.textContent = newCategory
        categoryList.appendChild(optionElement);
        const liElement = document.createElement("li")
        const filterDropdownList = document.getElementById("filterDropdownList")
        liElement.textContent = newCategory;
        liElement.addEventListener("click", function() {
            liElement.classList.toggle("category-checked")
            const checkedCategory = liElement.textContent;
            const categoryLinks = document.getElementById("categoryLinks");
            Array.from(categoryLinks.children).forEach((child) => {
                if (!child.textContent.includes(checkedCategory)) {
                    child.classList.add("hidden");
                }
            });
        })
        filterDropdownList.appendChild(liElement)
        alert(`Dodano kategorię: ${newCategory}`);
    }
});

// Handle document form submission
document.getElementById('documentForm').addEventListener('submit', (e) => {
    e.preventDefault();
    const title = document.getElementById('title').value;
    const description = document.getElementById('description').value;
    const category = document.getElementById('category').value;
    const file = document.getElementById('file').files[0];

    if (!title || !category || !file) {
        alert('Uzupełnij wszystkie wymagane pola!');
        return;
    }

    const categoryLinks = document.getElementById('categoryLinks');
    const newLink = document.createElement('li');

    fetch("http://localhost:8081/api/notes", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            category:  `${category}`,
            //TODO: WRZUCIĆ PRAWDZIWY CONTENT
            content: `TESTOWY CONTENT`,
            //TODO: WRZUCIĆ PRAWDZIWY IMAGEPATH
            imagePath: `TESTOWY IMAGEPATH`,
            title: `${title}`
        }),
    });

    newLink.textContent = `${title} (${category})`;
    categoryLinks.appendChild(newLink);

    alert('Dokument został dodany!');
});

// Handle dropdown visibility
document.querySelector('.dropdown-btn').addEventListener('click', function () {
    const dropdownContent = this.nextElementSibling
    dropdownContent.style.display = dropdownContent.style.display === 'block' ? 'none' : 'block'
});

// Handle displaying the category dropdown
document.getElementById("filterIcon").addEventListener("click", function () {
    const dropdown = document.getElementById("filterDropdownMenu");
    dropdown.classList.toggle("hidden")
})