package com.preformanceTracker.Performance_Tracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "admin")
public class Admin {

    // Getters and setters
    @Id
    private String adminId;

    private String adminPass;

}
