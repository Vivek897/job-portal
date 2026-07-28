package com.jobportal.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobResponse {

    private Long id;
    private String title;
    private String description;
    private String requiredSkills;
    private String location;
    private Double salaryMin;
    private Double salaryMax;
    private String jobType;
    private Integer experienceRequired;
    private String status;
    private Long recruiterId;
    private String recruiterName;
    private String companyName;
    private LocalDateTime createdAt;
}