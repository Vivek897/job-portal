package com.jobportal.backend.controller;

import com.jobportal.backend.config.JwtUtil;
import com.jobportal.backend.dto.JobRequest;
import com.jobportal.backend.dto.JobResponse;
import com.jobportal.backend.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<JobResponse> createJob(
            @Valid @RequestBody JobRequest request,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);

        return ResponseEntity.ok(jobService.createJob(request, email));
    }

    @GetMapping
    public ResponseEntity<Page<JobResponse>> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(jobService.getAllOpenJobs(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @GetMapping("/my-jobs")
    public ResponseEntity<List<JobResponse>> getMyJobs(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);

        return ResponseEntity.ok(jobService.getJobsByRecruiter(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobRequest request,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);

        return ResponseEntity.ok(jobService.updateJob(id, request, email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);

        jobService.deleteJob(id, email);
        return ResponseEntity.ok("Job deleted successfully");
    }
}