package com.preformanceTracker.Performance_Tracker.service;

import com.preformanceTracker.Performance_Tracker.entity.Grade;
import com.preformanceTracker.Performance_Tracker.entity.Student;
import com.preformanceTracker.Performance_Tracker.entity.Subject;
import com.preformanceTracker.Performance_Tracker.model.StudentDTO;
import com.preformanceTracker.Performance_Tracker.repository.GradeRepository;
import com.preformanceTracker.Performance_Tracker.repository.StudentRepository;
import com.preformanceTracker.Performance_Tracker.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private SubjectRepository subjectRepo;

    @Autowired
    private GradeRepository gradeRepo;

    public void saveStudentWithMarks(StudentDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setRollNumber(dto.getRollNumber());
        student.setYear(dto.getYear());
        student.setSection(dto.getSection());

        Student savedStudent = studentRepo.save(student);

        for (Map.Entry<Object, Object> entry : dto.getMarks().entrySet()) {
            Subject subject = subjectRepo.findByName((String) entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Subject not found: " + entry.getKey()));

            Grade grade = new Grade();
            grade.setStudent(savedStudent);
            grade.setSubject(subject);
            grade.setMarks((Integer) entry.getValue());

            gradeRepo.save(grade);
        }
    }
}
