// Handle adding a new category
document.getElementById('addCategory').addEventListener('click', () => {
    const newCategory = prompt('Wprowadź nową kategorię:');
    if (newCategory) {
        const categorySelect = document.getElementById('category');
        const newOption = document.createElement('option');
        newOption.value = newCategory;
        newOption.textContent = newCategory;
        categorySelect.appendChild(newOption);
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
    newLink.textContent = `${title} (${category})`;
    categoryLinks.appendChild(newLink);

    alert('Dokument został dodany!');
});

// Handle dropdown visibility
document.querySelector('.dropdown-btn').addEventListener('click', function () {
    const dropdownContent = this.nextElementSibling;
    dropdownContent.style.display = dropdownContent.style.display === 'block' ? 'none' : 'block';
});
