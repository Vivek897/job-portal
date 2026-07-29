package com.jobportal.backend.controller;

import com.jobportal.backend.config.JwtUtil;
import com.jobportal.backend.dto.ApplicationResponse;
import com.jobportal.backend.dto.ApplicationStatusUpdateRequest;
import com.jobportal.backend.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final JwtUtil jwtUtil;

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<ApplicationResponse> applyToJob(
            @PathVariable Long jobId,
            @RequestHeader("Authorization") String authHeader) {

        String email = extractEmail(authHeader);
        return ResponseEntity.ok(applicationService.applyToJob(jobId, email));
    }

    @GetMapping("/my-applications")
    public ResponseEntity<List<ApplicationResponse>> getMyApplications(
            @RequestHeader("Authorization") String authHeader) {

        String email = extractEmail(authHeader);
        return ResponseEntity.ok(applicationService.getMyApplications(email));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsForJob(
            @PathVariable Long jobId,
            @RequestHeader("Authorization") String authHeader) {

        String email = extractEmail(authHeader);
        return ResponseEntity.ok(applicationService.getApplicationsForJob(jobId, email));
    }

    @PutMapping("/{applicationId}/status")
    public ResponseEntity<ApplicationResponse> updateStatus(
            @PathVariable Long applicationId,
            @Valid @RequestBody ApplicationStatusUpdateRequest request,
            @RequestHeader("Authorization") String authHeader) {

        String email = extractEmail(authHeader);
        return ResponseEntity.ok(applicationService.updateApplicationStatus(applicationId, request.getStatus(), email));
    }

    private String extractEmail(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return jwtUtil.extractEmail(token);
    }
}