const gradeFilter = document.getElementById('grade-filter');
const tableBody = document.querySelector('#students-table tbody');
const showingCount = document.getElementById('showing-count');

// Loading state
function setLoading(isLoading) {
    if (isLoading) {
        tableBody.innerHTML = `<tr><td colspan="4" style="text-align:center;">Loading...</td></tr>`;
        showingCount.textContent = '';
    }
}

// Fetch and render students by grade
function fetchAndRenderByGrade() {
    const selectedGrade = gradeFilter.value;
    setLoading(true);

    fetch("/api/students/with-grades")
        .then(res => {
            if (!res.ok) throw new Error("API call failed");
            return res.json();
        })
        .then(students => {
            tableBody.innerHTML = "";

            const filtered = selectedGrade === "all"
                ? students
                : students.filter(s => s.grade === selectedGrade);

            if (filtered.length === 0) {
                tableBody.innerHTML = `<tr><td colspan="4" style="text-align:center;">No students found for grade ${selectedGrade}</td></tr>`;
                showingCount.textContent = "Showing 0 students";
                return;
            }

            filtered.forEach(student => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${student.name}<br><small>${student.rollNumber}</small></td>
                    <td>Year ${student.year}</td>
                    <td>${student.section}</td>
                    <td>${student.grade}</td>
                `;
                tableBody.appendChild(row);
            });

            showingCount.textContent = `Showing ${filtered.length} student${filtered.length !== 1 ? 's' : ''}`;
        })
        .catch(() => {
            tableBody.innerHTML = `<tr><td colspan="4" style="text-align:center;">Error loading students.</td></tr>`;
            showingCount.textContent = "Showing 0 students";
        });
}

// Bind change event
gradeFilter.addEventListener('change', fetchAndRenderByGrade);

// Initial render
fetchAndRenderByGrade();
