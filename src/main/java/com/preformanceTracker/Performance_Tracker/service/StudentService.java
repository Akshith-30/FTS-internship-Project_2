package com.preformanceTracker.Performance_Tracker.service;

import com.preformanceTracker.Performance_Tracker.dto.StudentDTO;
import com.preformanceTracker.Performance_Tracker.dto.StudentViewDTO;
import com.preformanceTracker.Performance_Tracker.entity.Grade;
import com.preformanceTracker.Performance_Tracker.entity.Student;
import com.preformanceTracker.Performance_Tracker.entity.Subject;
import com.preformanceTracker.Performance_Tracker.repository.GradeRepository;
import com.preformanceTracker.Performance_Tracker.repository.StudentRepository;
import com.preformanceTracker.Performance_Tracker.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private SubjectRepository subjectRepo;

    @Autowired
    private GradeRepository gradeRepo;

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public List<StudentDTO> getStudentsByYearAndSection(Integer year, String section) {
        List<Student> students;

        if (year != null && section != null && !section.isEmpty()) {
            students = studentRepo.findByYearAndSection(year, section);
        } else if (year != null) {
            students = studentRepo.findByYear(year);
        } else if (section != null && !section.isEmpty()) {
            students = studentRepo.findBySection(section);
        } else {
            students = studentRepo.findAll();
        }

        List<StudentDTO> result = new ArrayList<>();
        for (Student student : students) {
            StudentDTO dto = new StudentDTO();
            dto.setName(student.getName());
            dto.setRollNumber(student.getRollNumber());
            dto.setYear(student.getYear());
            dto.setSection(student.getSection());

            List<Grade> grades = gradeRepo.findByStudent(student);

            Map<String, Integer> marksMap = new HashMap<>();
            int totalMarks = 0;
            for (Grade grade : grades) {
                if (grade.getSubject() != null && grade.getSubject().getName() != null) {
                    marksMap.put(grade.getSubject().getName(), grade.getMarks());
                }
                totalMarks += grade.getMarks();
            }
            dto.setMarks(marksMap);
            dto.setTotalMarks(totalMarks);

            dto.setGrade(calculateGrade(totalMarks));

            result.add(dto);
        }
        return result;
    }

    private String calculateGrade(int totalMarks) {
        if (totalMarks >= 450) return "A";
        else if (totalMarks >= 400) return "B";
        else if (totalMarks >= 350) return "C";
        else return "D";
    }

    public void saveStudentWithMarks(StudentDTO dto) {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new IllegalArgumentException("Student name is required");
        }
        if (dto.getRollNumber() == null || dto.getRollNumber().isEmpty()) {
            throw new IllegalArgumentException("Roll number is required");
        }
        if (dto.getYear() < 1 || dto.getYear() > 4) {
            throw new IllegalArgumentException("Year must be between 1 and 4");
        }
        if (dto.getSection() == null || dto.getSection().isEmpty()) {
            throw new IllegalArgumentException("Section is required");
        }
        if (dto.getMarks() == null || dto.getMarks().isEmpty()) {
            throw new IllegalArgumentException("Marks cannot be empty");
        }

        Student student = new Student();
        student.setName(dto.getName());
        student.setRollNumber(dto.getRollNumber());
        student.setYear(dto.getYear());
        student.setSection(dto.getSection());

        Student savedStudent = studentRepo.save(student);

        for (Map.Entry<String, Integer> entry : dto.getMarks().entrySet()) {
            String subjectName = entry.getKey();
            Integer marks = entry.getValue();

            Optional<Subject> optionalSubject = subjectRepo.findByNameIgnoreCase(subjectName);
            if (optionalSubject.isEmpty()) {
                throw new RuntimeException("Subject not found: " + subjectName);
            }

            Subject subject = optionalSubject.get();

            if (marks == null || marks < 0 || marks > 100) {
                throw new IllegalArgumentException("Marks for " + subjectName + " must be between 0 and 100");
            }

            Grade grade = new Grade();
            grade.setStudent(savedStudent);
            grade.setSubject(subject);
            grade.setMarks(marks);

            gradeRepo.save(grade);
        }
    }

    public Map<Integer, List<Map<String, Object>>> getTop3PerformersByYear() {
        List<Student> students = studentRepo.findAll();

        List<Map<String, Object>> studentScores = students.stream().map(student -> {
            int totalMarks = gradeRepo.findByStudent(student)
                    .stream()
                    .mapToInt(Grade::getMarks)
                    .sum();

            Map<String, Object> info = new LinkedHashMap<>();
            info.put("id", student.getId());
            info.put("name", student.getName());
            info.put("rollNumber", student.getRollNumber());
            info.put("section", student.getSection());
            info.put("year", student.getYear());
            info.put("totalMarks", totalMarks);
            return info;
        }).collect(Collectors.toList());

        return studentScores.stream()
                .collect(Collectors.groupingBy(
                        s -> (Integer) s.get("year"),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted((a, b) -> Integer.compare(
                                                (int) b.get("totalMarks"), (int) a.get("totalMarks")
                                        ))
                                        .limit(3)
                                        .collect(Collectors.toList())
                        )
                ));
    }

    public List<StudentViewDTO> getAllStudentViews() {
        List<Student> students = studentRepo.findAll();

        return students.stream().map(student -> {
            List<Grade> grades = gradeRepo.findByStudent(student);
            int totalMarks = grades.stream().mapToInt(Grade::getMarks).sum();
            int subjectCount = grades.size();

            double percentage = subjectCount == 0 ? 0 : (totalMarks / (subjectCount * 100.0)) * 100;

            String grade;
            if (percentage >= 80) {
                grade = "A";
            } else if (percentage >= 60) {
                grade = "B";
            } else {
                grade = "C";
            }

            StudentViewDTO dto = new StudentViewDTO();
            dto.setId(student.getId());
            dto.setName(student.getName());
            dto.setRollNumber(student.getRollNumber());
            dto.setYear(student.getYear());
            dto.setSection(student.getSection());
            dto.setGrade(grade);

            return dto;
        }).collect(Collectors.toList());
    }
}
