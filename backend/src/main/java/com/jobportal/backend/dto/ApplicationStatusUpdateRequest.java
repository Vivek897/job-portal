package com.jobportal.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApplicationStatusUpdateRequest {

    @NotBlank(message = "Status is required")
    private String status; // SHORTLISTED, REJECTED, HIRED
}
