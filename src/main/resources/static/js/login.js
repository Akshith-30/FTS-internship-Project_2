document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    const adminIdInput = document.getElementById("adminId");
    const adminPassInput = document.getElementById("adminPass");

    form.addEventListener("submit", function (e) {
        e.preventDefault();

        const adminId = adminIdInput.value.trim();
        const adminPass = adminPassInput.value.trim();

        // Check if both fields are filled
        if (!adminId || !adminPass) {
            alert("Both fields are required.");
            return;
        }

        // Send login request to backend
        fetch("/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                adminId: adminId,
                adminPass: adminPass
            })
        })
        .then(response => {
            if (response.ok) {
                // ✅ Login successful → redirect to dashboard
                window.location.href = "/html/dashboard.html";
            } else {
                alert("Invalid admin ID or password.");
            }
        })
        .catch(error => {
            console.error("Login error:", error);
            alert("An error occurred. Please try again later.");
        });
    });
});
