package com.jobportal.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Required skills are required")
    private String requiredSkills;

    @NotBlank(message = "Location is required")
    private String location;

    private Double salaryMin;
    private Double salaryMax;

    @NotNull(message = "Job type is required")
    private String jobType; // FULL_TIME, PART_TIME, INTERNSHIP, REMOTE

    private Integer experienceRequired;
}