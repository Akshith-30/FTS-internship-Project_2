package com.preformanceTracker.Performance_Tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
@Getter
@Setter
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String rollNumber;
    private int year;
    private String section;
    private String grade; // optional

    @CreationTimestamp
    private Timestamp createdAt;

    // Getters and Setters
}
