const yearFilter = document.getElementById('year-filter');
const tableBody = document.querySelector('#failed-students-table tbody');
const showingCount = document.getElementById('showing-count');

// Optional: Add a loading indicator
function setLoading(isLoading) {
    if (isLoading) {
        tableBody.innerHTML = `<tr><td colspan="4" style="text-align:center;">Loading...</td></tr>`;
        showingCount.textContent = '';
    }
}

function fetchFailedStudents() {
    const year = yearFilter.value;

    // ✅ Updated URL format to match Spring Boot controller expecting path variable
    let url = "/api/failed-students";
    if (year) url += `/${year}`;

    setLoading(true);

    fetch(url)
        .then(res => {
            if (!res.ok) throw new Error("API error");
            return res.json();
        })
        .then(data => {
            tableBody.innerHTML = "";

            if (!Array.isArray(data) || data.length === 0) {
                tableBody.innerHTML = `<tr><td colspan="4" style="text-align:center;">No failed students found.</td></tr>`;
                showingCount.textContent = "Showing 0 of 0 failed students";
                return;
            }

            data.forEach(student => {
                const failedSubjects = Object.entries(student.subjectMarks || {})
                    .map(([subject, marks]) => `${subject}: ${marks}`)
                    .join("<br>");

                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${student.name}<br><small>${student.rollNumber}</small></td>
                    <td>${student.year}</td>
                    <td>${student.section}</td>
                    <td>${failedSubjects}</td>
                `;
                tableBody.appendChild(row);
            });

            showingCount.textContent = `Showing ${data.length} failed student${data.length !== 1 ? 's' : ''}`;
        })
        .catch(err => {
            console.error("Error fetching failed students:", err);
            tableBody.innerHTML = "<tr><td colspan='4' style='text-align:center;'>Error loading data</td></tr>";
            showingCount.textContent = "Showing 0 of 0 failed students";
        });
}

// Attach event listener
yearFilter.addEventListener('change', fetchFailedStudents);

// Initial load
fetchFailedStudents();
