const yearFilter = document.getElementById('year-filter');
const tableBody = document.querySelector('#failed-students-table tbody');
const showingCount = document.getElementById('showing-count');

function getSection(rollNumber) {
    return rollNumber % 2 === 0 ? 'B' : 'A';
}

function setLoading(isLoading) {
    if (isLoading) {
        tableBody.innerHTML = `<tr><td colspan="4" style="text-align:center;">Loading...</td></tr>`;
        showingCount.textContent = '';
    }
}

function fetchFailedStudents() {
    const year = yearFilter.value.trim();
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

                const section = student.section || getSection(student.rollNumber || 0);

                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${student.name}<br><small>${student.rollNumber}</small></td>
                    <td>${student.year}</td>
                    <td>${section}</td>
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

yearFilter.addEventListener('change', fetchFailedStudents);
fetchFailedStudents();
