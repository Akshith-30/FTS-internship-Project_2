package com.preformanceTracker.Performance_Tracker.repository;

import com.preformanceTracker.Performance_Tracker.entity.Grade;
import com.preformanceTracker.Performance_Tracker.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
        List<Grade> findByStudent(Student student);
        List<Object[]> findAverageMarksPerStudent();
}



