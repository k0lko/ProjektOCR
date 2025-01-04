document.addEventListener("DOMContentLoaded", function() {
    const noteForm = document.getElementById("noteForm");
    const notesList = document.getElementById("notesList");

    // Fetch all notes and display them
    fetchNotes();

    // Handle note form submission
    noteForm.addEventListener("submit", function(event) {
        event.preventDefault();

        const formData = new FormData(noteForm);

        fetch("/api/notes", {
            method: "POST",
            body: formData,
        })
            .then(response => response.json())
            .then(data => {
                fetchNotes();
                noteForm.reset();
            })
            .catch(error => console.error("Error:", error));
    });

    // Function to fetch all notes from the backend
    function fetchNotes() {
        fetch("/api/notes")
            .then(response => response.json())
            .then(notes => {
                notesList.innerHTML = "";
                notes.forEach(note => {
                    const noteElement = document.createElement("li");
                    noteElement.textContent = `${note.title} - ${note.category}`;
                    notesList.appendChild(noteElement);
                });
            })
            .catch(error => console.error("Error:", error));
    }
});
