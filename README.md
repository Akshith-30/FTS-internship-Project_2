# FTS_SpringProjects_2

# SQL

```sql
-- 1. Create Schema and Use It
CREATE SCHEMA IF NOT EXISTS performance_tracker;
USE performance_tracker;

-- 2. Create Students Table
CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    roll_number VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    year INT NOT NULL,
    section VARCHAR(1) NOT NULL,
    grade CHAR(1),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Create Subjects Table
CREATE TABLE IF NOT EXISTS subjects (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- 4. Create Grades Table with Foreign Keys and Constraints
CREATE TABLE IF NOT EXISTS grades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    subject_id INT NOT NULL,
    marks INT NOT NULL CHECK (marks BETWEEN 0 AND 100),
    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    CONSTRAINT fk_subject FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,
    CONSTRAINT uq_grade UNIQUE (student_id, subject_id)
);

-- 5. Create Admin Table
CREATE TABLE IF NOT EXISTS admin (
    admin_id VARCHAR(50) PRIMARY KEY,
    admin_pass VARCHAR(255) NOT NULL
);

-- 6. Insert Initial Admin Credentials
INSERT INTO admin (admin_id, admin_pass)
VALUES ('admin123', 'Admin@123')
ON DUPLICATE KEY UPDATE admin_pass = VALUES(admin_pass);

-- 7. Insert Sample Subjects
INSERT INTO subjects (name) VALUES
('Mathematics'),
('Physics'),
('Chemistry'),
('English'),
('Computer Science');

-- 8. Insert Students

-- 9. Insert Grades for All Students

-- 10. Verification Queries
SELECT * FROM students;
SELECT * FROM subjects;
SELECT * FROM grades;
SELECT * FROM admin;
```
