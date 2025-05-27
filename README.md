create schema performance_tracker;
use performance_tracker;

CREATE TABLE students (
id INT AUTO_INCREMENT PRIMARY KEY,
roll_number VARCHAR(50) UNIQUE NOT NULL,
name VARCHAR(100) NOT NULL,
year INT NOT NULL,
section VARCHAR(1) NOT NULL,
grade CHAR(1),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE subjects (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50) NOT NULL
);

CREATE TABLE grades (
id INT AUTO_INCREMENT PRIMARY KEY,
student_id INT NOT NULL,
subject_id INT NOT NULL,
marks INT NOT NULL CHECK (marks BETWEEN 0 AND 100),

    CONSTRAINT fk_student
        FOREIGN KEY (student_id)
        REFERENCES students(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_subject
        FOREIGN KEY (subject_id)
        REFERENCES subjects(id)
        ON DELETE CASCADE
);
ALTER TABLE grades ADD CONSTRAINT uq_grade UNIQUE (student_id, subject_id);

CREATE TABLE admin (
admin_id VARCHAR(50) PRIMARY KEY,
admin_pass VARCHAR(255) NOT NULL
);

INSERT INTO admin (admin_id, admin_pass)
VALUES ('admin123', 'adminpass');

select * from grades;
select * from students;
select * from subjects;
select * from admin;

INSERT INTO subjects (name) VALUES
('Mathematics'),
('Physics'),
('Chemistry'),
('English'),
('Computer Science');
