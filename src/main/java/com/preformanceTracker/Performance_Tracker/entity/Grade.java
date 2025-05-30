// Grade.java
package com.preformanceTracker.Performance_Tracker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private int marks;

    @Setter
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonBackReference(value = "student-grade")
    private Student student;

    @Setter
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    @JsonBackReference(value = "subject-grade")
    private Subject subject;
}
