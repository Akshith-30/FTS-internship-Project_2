async function fetchStudents() {
  const response = await fetch('/api/students');
  return await response.json();
}

let studentsData = [];

async function filterAndRender() {
  const year = document.getElementById('yearFilter').value;
  const tbody = document.getElementById('studentRows');
  const noData = document.getElementById('noStudents');

  tbody.innerHTML = '';
  let found = false;

  if (studentsData.length === 0) {
    studentsData = await fetchStudents();
  }

  studentsData.forEach(student => {
    const matchesYear = year === 'all' || student.year === parseInt(year);
    if (matchesYear) {
      const total = Object.values(student.marks).reduce((sum, val) => sum + val, 0);
      let grade = '';
      const percentage = (total / 500) * 100;
      if (percentage >= 80) grade = 'A';
      else if (percentage >= 60) grade = 'B';
      else grade = 'C';

      const row = document.createElement('div');
      row.className = 'student-row';
      row.innerHTML = `
        <span><strong>${student.name}</strong> (${student.rollNumber})</span>
        <span>${student.year}</span>
        <span>${total}</span>
        <span>${grade}</span>
      `;
      tbody.appendChild(row);
      found = true;
    }
  });

  noData.style.display = found ? 'none' : 'block';
}

document.getElementById('yearFilter').addEventListener('change', filterAndRender);

// Initial fetch and render
fetchStudents().then(data => {
  studentsData = data;
  filterAndRender();
});
