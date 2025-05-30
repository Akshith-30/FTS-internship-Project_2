package com.preformanceTracker.Performance_Tracker.repository;

import com.preformanceTracker.Performance_Tracker.entity.Grade;
import com.preformanceTracker.Performance_Tracker.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

        // Get all grades for a student
        List<Grade> findByStudent(Student student);

        // Custom query: Get average marks per student
        @Query("SELECT g.student.id, AVG(g.marks) FROM Grade g GROUP BY g.student.id")
        List<Object[]> findAverageMarksPerStudent();

        // Get failed grades by year and marks below passing
        List<Grade> findByYearAndMarksLessThan(int year, int marks);
}
