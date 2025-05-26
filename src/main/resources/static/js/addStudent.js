document.addEventListener("DOMContentLoaded", async () => {
  const container = document.getElementById("subjectMarksContainer");

  try {
    const response = await fetch("/api/subjects");
    const subjects = await response.json();

    subjects.forEach(subject => {
      const div = document.createElement("div");
      div.className = "input-box";

      div.innerHTML = `
        <label>${subject.name}</label>
        <input type="hidden" name="subjects[]" value="${subject.name}" />
        <input type="number" name="marks[]" placeholder="Marks for ${subject.name}" required />
      `;

      container.appendChild(div);
    });
  } catch (err) {
    console.error("Failed to load subjects", err);
    container.innerHTML += `<p style="color:red;">Failed to load subjects</p>`;
  }
});

document.getElementById("addStudentForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const formData = new FormData(e.target);

  const marks = {};
  for (const [key, value] of formData.entries()) {
    if (key.startsWith("marks[")) {
      const subjectName = key.match(/marks\[(.+)\]/)[1];
      marks[subjectName] = parseInt(value);
    }
  }

  const studentData = {
    name: formData.get("name"),
    rollNumber: formData.get("rollNumber"),
    year: parseInt(formData.get("year")),
    section: formData.get("section"),
    marks: marks,
  };

  try {
    const response = await fetch("/api/students", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(studentData),
    });

    if (response.ok) {
      alert("Student added successfully!");
      e.target.reset();
      document.getElementById("subjectMarksContainer").innerHTML = "<h3>Subject Marks</h3>";
    } else {
      const result = await response.json();
      alert("Failed to add student: " + result.message || "Unknown error");
    }
  } catch (error) {
    alert("Error: " + error.message);
  }
});
