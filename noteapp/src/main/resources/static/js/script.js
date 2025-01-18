document.addEventListener("DOMContentLoaded", function () {
    // Pobierz kategorie i utwórz elementy listy
    fetch("http://localhost:8081/api/categories")
        .then((response) => {
            if (!response.ok) {
                throw new Error("Błąd podczas pobierania kategorii.");
            }
            return response.json();
        })
        .then((categories) => {
            const categoryList = document.getElementById("category");
            const filterDropdownList = document.getElementById("filterDropdownList");

            categories.forEach((category) => {
                // Dodaj opcję do selecta kategorii
                const optionElement = document.createElement("option");
                optionElement.value = category.name;
                optionElement.textContent = category.name;
                categoryList.appendChild(optionElement);

                // Dodaj element do listy filtrów
                const liElement = document.createElement("li");
                liElement.textContent = category.name;
                liElement.addEventListener("click", function () {
                    liElement.classList.toggle("category-checked");
                    filterNotes();
                });
                filterDropdownList.appendChild(liElement);
            });

            // Pobierz notatki i wyświetl je na liście
            fetch("http://localhost:8081/api/notes")
                .then((response) => {
                    if (!response.ok) {
                        throw new Error("Błąd podczas pobierania notatek.");
                    }
                    return response.json();
                })
                .then((notes) => {
                    displayNotes(notes);
                })
                .catch((error) => alert(error.message));
        })
        .catch((error) => alert(error.message));

    // Funkcja filtrująca notatki
    function filterNotes() {
        const checkedCategories = Array.from(
            document.querySelectorAll("#filterDropdownList .category-checked")
        ).map((li) => li.textContent);

        const categoryLinks = document.getElementById("categoryLinks");
        Array.from(categoryLinks.children).forEach((child) => {
            const noteCategory = child.textContent.split(" (")[1].slice(0, -1);
            if (checkedCategories.length === 0 || checkedCategories.includes(noteCategory)) {
                child.classList.remove("hidden");
            } else {
                child.classList.add("hidden");
            }
        });
    }

    // Funkcja wyświetlająca notatki
    function displayNotes(notes) {
        const categoryLinks = document.getElementById("categoryLinks");
        categoryLinks.innerHTML = "";

        notes.forEach((note) => {
            const liElement = document.createElement("li");
            liElement.textContent = `${note.title} (${note.category.name})`;
            categoryLinks.appendChild(liElement);
        });
    }

    // Obsługa dodawania nowej kategorii
    document.getElementById("addCategory").addEventListener("click", () => {
        const newCategory = prompt("Wprowadź nową kategorię:");
        if (newCategory) {
            const categoryList = document.getElementById("category");
            const filterDropdownList = document.getElementById("filterDropdownList");

            // Dodaj opcję do selecta kategorii
            const optionElement = document.createElement("option");
            optionElement.value = newCategory;
            optionElement.textContent = newCategory;
            categoryList.appendChild(optionElement);

            // Dodaj element do listy filtrów
            const liElement = document.createElement("li");
            liElement.textContent = newCategory;
            liElement.addEventListener("click", function () {
                liElement.classList.toggle("category-checked");
                filterNotes();
            });
            filterDropdownList.appendChild(liElement);

            alert(`Dodano kategorię: ${newCategory}`);
        }
    });

    // Obsługa przesyłania formularza dokumentu
    document.getElementById("documentForm").addEventListener("submit", (e) => {
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

        // TODO: Dodaj logikę wysyłania danych formularza do serwera (np. za pomocą Fetch API)
        // ...

        newLink.textContent = `${title} (${category})`;
        categoryLinks.appendChild(newLink);

        alert('Dokument został dodany!');
    });

    // Obsługa widoczności dropdown
    document.querySelector(".dropdown-btn").addEventListener("click", function () {
        this.nextElementSibling.classList.toggle("show");
    });

    // Obsługa wyświetlania dropdown kategorii
    document.getElementById("filterIcon").addEventListener("click", function () {
        document.getElementById("filterDropdownMenu").classList.toggle("hidden");
    });
    // Function to create the table and populate it with data
    function createTable(data) {
        const tableBody = document.getElementById('dataTable').getElementsByTagName('tbody')[0];

        data.forEach((document, index) => {
            const row = tableBody.insertRow(index);

            const cell1 = row.insertCell(0);
            cell1.textContent = index + 1;

            const cell2 = row.insertCell(1);
            cell2.textContent = document.title;

            const cell3 = row.insertCell(2);
            cell3.textContent = document.description;

            const cell4 = row.insertCell(3);
            cell4.textContent = document.category;

            const cell5 = row.insertCell(4);
            cell5.innerHTML = `
            <a href="/view/${document.id}" class="btn btn-primary btn-sm">Otwórz</a>
            <button class="btn btn-danger btn-sm" onclick="deleteDocument(${document.id})">Usuń</button>
        `;
        });
    }

// Call the function to create the table after the document has loaded
    document.addEventListener('DOMContentLoaded', () => {
        fetch('/get-documents')
            .then(response => response.json())
            .then(data => createTable(data))
            .catch(error => console.error('Error fetching documents:', error));
    });

// Function to delete a document
    function deleteDocument(id) {
        // Send a DELETE request to the server to delete the document
        fetch(`/delete/${id}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    // Remove the deleted document from the table
                    const row = document.getElementById(`row-${id}`);
                    row.parentNode.removeChild(row);
                } else {
                    console.error('Error deleting document:', response.statusText);
                }
            });
    }
});

// Function to create the table and populate it with data
function createTable(data) {
    const tableBody = document.getElementById('dataTable').getElementsByTagName('tbody')[0];

    data.forEach((document, index) => {
        const row = tableBody.insertRow(index);

        const cell1 = row.insertCell(0);
        cell1.textContent = index + 1;

        const cell2 = row.insertCell(1);
        cell2.textContent = document.title;

        const cell3 = row.insertCell(2);
        cell3.textContent = document.description;

        const cell4 = row.insertCell(3);
        cell4.textContent = document.category;

        const cell5 = row.insertCell(4);
        cell5.innerHTML = `
            <a href="/view/${document.id}" class="btn btn-primary btn-sm">Otwórz</a>
            <button class="btn btn-danger btn-sm" onclick="deleteDocument(${document.id})">Usuń</button>
        `;
    });
}

// Call the function to create the table after the document has loaded
document.addEventListener('DOMContentLoaded', () => {
    fetch('/get-documents')
        .then(response => response.json())
        .then(data => createTable(data))
        .catch(error => console.error('Error fetching documents:', error));
});

// Function to delete a document
function deleteDocument(id) {
    // Send a DELETE request to the server to delete the document
    fetch(`/delete/${id}`, { method: 'DELETE' })
        .then(response => {
            if (response.ok) {
                // Remove the deleted document from the table
                const row = document.getElementById(`row-${id}`);
                row.parentNode.removeChild(row);
            } else {
                console.error('Error deleting document:', response.statusText);
            }
        });
}