package com.preformanceTracker.Performance_Tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Subject subject;

    private int marks;

    // Getters and Setters
}
