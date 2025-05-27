async function loadSubjects() {
  const container = document.getElementById("subjectMarksContainer");
  container.innerHTML = "";

  const heading = document.createElement("h3");
  heading.className = "section-heading";
  heading.textContent = "Subject Marks";
  container.appendChild(heading);

  try {
    const response = await fetch("/api/subjects");
    if (!response.ok) throw new Error("Failed to load subjects");
    const subjects = await response.json();

    subjects.forEach(subject => {
      const div = document.createElement("div");
      div.className = "input-box";

      div.innerHTML = `
        <input type="hidden" name="subjects[]" value="${subject.name}" />
        <input
          type="number"
          name="marks[]"
          placeholder="Marks for ${subject.name}"
          required
          min="0"
          max="100"
        />
      `;

      container.appendChild(div);
    });
  } catch (err) {
    console.error("Failed to load subjects", err);
    container.innerHTML += `<p style="color:red;">Failed to load subjects</p>`;
  }
}

document.addEventListener("DOMContentLoaded", loadSubjects);

document.getElementById("addStudentForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const formData = new FormData(e.target);

  const marks = {};
  const subjects = formData.getAll("subjects[]");
  const marksArr = formData.getAll("marks[]");

  for (let i = 0; i < subjects.length; i++) {
    marks[subjects[i]] = parseInt(marksArr[i]);
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
      await loadSubjects();
    } else {
      let result = {};
      try {
        result = await response.json();
      } catch {
        alert("Failed to add student: Unknown error");
        return;
      }
      alert("Failed to add student: " + (result.message || "Unknown error"));
    }
  } catch (error) {
    alert("Error: " + error.message);
  }
});
