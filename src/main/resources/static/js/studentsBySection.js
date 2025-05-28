const yearFilter = document.getElementById('year-filter');
const sectionFilter = document.getElementById('section-filter');
const tableBody = document.querySelector('#students-table tbody');
const showingCount = document.getElementById('showing-count');

// Optional: Add a loading indicator
function setLoading(isLoading) {
    if (isLoading) {
        tableBody.innerHTML = `<tr><td colspan="3" style="text-align:center;">Loading...</td></tr>`;
        showingCount.textContent = '';
    }
}

function fetchAndRenderStudents() {
    const year = yearFilter.value;
    const section = sectionFilter.value;

    // Build the query string
    const query = [];
    if (year) query.push(`year=${year}`);
    if (section) query.push(`section=${section}`);
    let url = "/api/students/filter";
    if (query.length > 0) url += "?" + query.join("&");

    setLoading(true);

    fetch(url)
        .then(res => {
            if (!res.ok) throw new Error("API error");
            return res.json();
        })
        .then(students => {
            tableBody.innerHTML = "";

            if (!Array.isArray(students) || students.length === 0) {
                tableBody.innerHTML = `<tr><td colspan="3" style="text-align:center;">No students found for the selected filter.</td></tr>`;
                showingCount.textContent = "Showing 0 students";
                return;
            }

            students.forEach(student => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${student.name}<br><small>${student.rollNumber}</small></td>
                    <td>Year ${student.year}</td>
                    <td>${student.section}</td>
                `;
                tableBody.appendChild(row);
            });
            showingCount.textContent = `Showing ${students.length} student${students.length !== 1 ? 's' : ''}`;
        })
        .catch(err => {
            tableBody.innerHTML = "<tr><td colspan='3' style='text-align:center;'>Error loading students</td></tr>";
            showingCount.textContent = "Showing 0 of 0 students";
        });
}

// Attach event listeners to the filters
yearFilter.addEventListener('change', fetchAndRenderStudents);
sectionFilter.addEventListener('change', fetchAndRenderStudents);

// Initial table load
fetchAndRenderStudents();
