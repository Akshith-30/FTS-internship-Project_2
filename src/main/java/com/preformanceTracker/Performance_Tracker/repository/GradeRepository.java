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

    // ✅ New native query added from CODE 1
    @Query(value = """
        SELECT s.name AS subjectName, st.name AS studentName, g.marks AS marks, st.year AS year
        FROM grades g
        JOIN students st ON g.student_id = st.id
        JOIN subjects s ON g.subject_id = s.id
        ORDER BY s.name, st.year, g.marks DESC
        """, nativeQuery = true)
    List<Object[]> findAllSortedForTop3();
}
