package com.jobportal.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recruiter_id", nullable = false)
    private User recruiter;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false)
    private String requiredSkills; // comma-separated for now, e.g. "Java,Spring,SQL"

    @Column(nullable = false)
    private String location;

    private Double salaryMin;
    private Double salaryMax;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType jobType;

    private Integer experienceRequired;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status = JobStatus.OPEN;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum JobType {
        FULL_TIME, PART_TIME, INTERNSHIP, REMOTE
    }

    public enum JobStatus {
        OPEN, CLOSED
    }
}
