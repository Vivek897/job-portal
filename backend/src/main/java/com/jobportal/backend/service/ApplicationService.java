package com.jobportal.backend.service;

import com.jobportal.backend.dto.ApplicationResponse;
import com.jobportal.backend.entity.Application;
import com.jobportal.backend.entity.Job;
import com.jobportal.backend.entity.User;
import com.jobportal.backend.repository.ApplicationRepository;
import com.jobportal.backend.repository.JobRepository;
import com.jobportal.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public ApplicationResponse applyToJob(Long jobId, String candidateEmail) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        User candidate = userRepository.findByEmail(candidateEmail)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        if (candidate.getRole() != User.Role.CANDIDATE) {
            throw new RuntimeException("Only candidates can apply to jobs");
        }

        if (applicationRepository.existsByJobAndCandidate(job, candidate)) {
            throw new RuntimeException("You have already applied to this job");
        }

        Application application = new Application();
        application.setJob(job);
        application.setCandidate(candidate);
        application.setStatus(Application.ApplicationStatus.APPLIED);

        Application savedApplication = applicationRepository.save(application);

        return mapToResponse(savedApplication);
    }

    public List<ApplicationResponse> getMyApplications(String candidateEmail) {

        User candidate = userRepository.findByEmail(candidateEmail)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        List<Application> applications = applicationRepository.findByCandidate(candidate);
        return applications.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<ApplicationResponse> getApplicationsForJob(Long jobId, String recruiterEmail) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getRecruiter().getEmail().equals(recruiterEmail)) {
            throw new RuntimeException("You are not authorized to view these applications");
        }

        List<Application> applications = applicationRepository.findByJob(job);
        return applications.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public ApplicationResponse updateApplicationStatus(Long applicationId, String newStatus, String recruiterEmail) {

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (!application.getJob().getRecruiter().getEmail().equals(recruiterEmail)) {
            throw new RuntimeException("You are not authorized to update this application");
        }

        application.setStatus(Application.ApplicationStatus.valueOf(newStatus.toUpperCase()));
        Application updatedApplication = applicationRepository.save(application);

        return mapToResponse(updatedApplication);
    }

    private ApplicationResponse mapToResponse(Application application) {
        return new ApplicationResponse(
                application.getId(),
                application.getJob().getId(),
                application.getJob().getTitle(),
                application.getJob().getRecruiter().getName(),
                application.getCandidate().getId(),
                application.getCandidate().getName(),
                application.getCandidate().getEmail(),
                application.getStatus().name(),
                application.getAppliedAt()
        );
    }
}