package com.preformanceTracker.Performance_Tracker.repository;

import com.preformanceTracker.Performance_Tracker.entity.Grade;
import com.preformanceTracker.Performance_Tracker.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    // Get all grades for a given student
    List<Grade> findByStudent(Student student);

    // Get failed grades by year (e.g., marks < 35)
    @Query("SELECT g FROM Grade g WHERE g.student.year = :year AND g.marks < :marks")
    List<Grade> findByStudentYearAndMarksLessThan(int year, int marks);

    // Get all failed grades regardless of year
    @Query("SELECT g FROM Grade g WHERE g.marks < :marks")
    List<Grade> findByMarksLessThan(int marks);

    /**
     * ✅ Get Top 3 Students per Subject per Year
     * - Uses native SQL with window function ROW_NUMBER() to rank students
     * - Returns: subjectName, studentName, rollNumber, marks, year
     * - Works only on MySQL 8.0+ / PostgreSQL / modern DBs
     */
    @Query(value = """
        SELECT subjectName, studentName, rollNumber, marks, year FROM (
            SELECT 
                s.name AS subjectName,
                st.name AS studentName,
                st.roll_number AS rollNumber,
                g.marks AS marks,
                st.year AS year,
                ROW_NUMBER() OVER (PARTITION BY s.name, st.year ORDER BY g.marks DESC) as `rank`
            FROM grades g
            JOIN students st ON g.student_id = st.id
            JOIN subjects s ON g.subject_id = s.id
        ) ranked
        WHERE `rank` <= 3
        ORDER BY subjectName, year, marks DESC
        """, nativeQuery = true)
    List<Object[]> findAllSortedForTop3();

    /**
     * 🔍 Optional: Calculates average marks per student
     * - Can be used for generating overall performance reports
     */
    @Query("SELECT g.student.id, AVG(g.marks) FROM Grade g GROUP BY g.student.id")
    List<Object[]> findAverageMarksPerStudent();
}
