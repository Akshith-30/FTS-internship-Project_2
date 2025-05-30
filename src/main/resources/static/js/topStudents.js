document.addEventListener("DOMContentLoaded", () => {
  const subjectFilter = document.getElementById("subject-filter");
  const yearFilter = document.getElementById("year-filter");
  const tableBody = document.querySelector("#top-students-table tbody");
  const showingCount = document.getElementById("showing-count");

  let allData = [];

  // Fetch top students data from backend
  async function fetchTopStudents() {
    try {
      const response = await fetch("/api/subjects/toppers");
      if (!response.ok) throw new Error("Failed to fetch data");
      allData = await response.json();
      populateSubjectFilter();
      renderTable();
    } catch (error) {
      console.error(error);
      tableBody.innerHTML = `<tr><td colspan="4">Error loading data.</td></tr>`;
    }
  }

  // Populate unique subjects in subject filter dropdown
  function populateSubjectFilter() {
    const subjects = [...new Set(allData.map(item => item.subjectName))];
    subjects.forEach(subject => {
      const option = document.createElement("option");
      option.value = subject;
      option.textContent = subject;
      subjectFilter.appendChild(option);
    });
  }

  // Filter and render table rows
  function renderTable() {
    const subjectValue = subjectFilter.value;
    const yearValue = yearFilter.value;

    let filtered = allData;

    if (subjectValue) {
      filtered = filtered.filter(item => item.subjectName === subjectValue);
    }
    if (yearValue) {
      filtered = filtered.filter(item => item.year === parseInt(yearValue));
    }

    tableBody.innerHTML = "";

    if (filtered.length === 0) {
      tableBody.innerHTML = `<tr><td colspan="4">No records found.</td></tr>`;
    } else {
      filtered.forEach(({ subjectName, studentName, marks, year }) => {
        const tr = document.createElement("tr");

        tr.innerHTML = `
          <td>${subjectName}</td>
          <td>${studentName}</td>
          <td>${marks}</td>
          <td>${year}</td>
        `;
        tableBody.appendChild(tr);
      });
    }

    showingCount.textContent = `Showing ${filtered.length} record${filtered.length !== 1 ? 's' : ''}`;
  }

  // Event listeners for filters
  subjectFilter.addEventListener("change", renderTable);
  yearFilter.addEventListener("change", renderTable);

  fetchTopStudents();
});
//