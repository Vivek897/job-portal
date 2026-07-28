package com.jobportal.backend.service;

import com.jobportal.backend.dto.JobRequest;
import com.jobportal.backend.dto.JobResponse;
import com.jobportal.backend.entity.Job;
import com.jobportal.backend.entity.User;
import com.jobportal.backend.repository.JobRepository;
import com.jobportal.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobResponse createJob(JobRequest request, String recruiterEmail) {

        User recruiter = userRepository.findByEmail(recruiterEmail)
                .orElseThrow(() -> new RuntimeException("Recruiter not found"));

        Job job = new Job();
        job.setRecruiter(recruiter);
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setRequiredSkills(request.getRequiredSkills());
        job.setLocation(request.getLocation());
        job.setSalaryMin(request.getSalaryMin());
        job.setSalaryMax(request.getSalaryMax());
        job.setJobType(Job.JobType.valueOf(request.getJobType().toUpperCase()));
        job.setExperienceRequired(request.getExperienceRequired());
        job.setStatus(Job.JobStatus.OPEN);

        Job savedJob = jobRepository.save(job);

        return mapToResponse(savedJob);
    }

    public Page<JobResponse> getAllOpenJobs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Job> jobs = jobRepository.findByStatus(Job.JobStatus.OPEN, pageable);
        return jobs.map(this::mapToResponse);
    }

    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return mapToResponse(job);
    }

    public List<JobResponse> getJobsByRecruiter(String recruiterEmail) {
        User recruiter = userRepository.findByEmail(recruiterEmail)
                .orElseThrow(() -> new RuntimeException("Recruiter not found"));

        List<Job> jobs = jobRepository.findByRecruiter(recruiter);
        return jobs.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public JobResponse updateJob(Long id, JobRequest request, String recruiterEmail) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getRecruiter().getEmail().equals(recruiterEmail)) {
            throw new RuntimeException("You are not authorized to update this job");
        }

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setRequiredSkills(request.getRequiredSkills());
        job.setLocation(request.getLocation());
        job.setSalaryMin(request.getSalaryMin());
        job.setSalaryMax(request.getSalaryMax());
        job.setJobType(Job.JobType.valueOf(request.getJobType().toUpperCase()));
        job.setExperienceRequired(request.getExperienceRequired());

        Job updatedJob = jobRepository.save(job);
        return mapToResponse(updatedJob);
    }

    public void deleteJob(Long id, String recruiterEmail) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getRecruiter().getEmail().equals(recruiterEmail)) {
            throw new RuntimeException("You are not authorized to delete this job");
        }

        jobRepository.delete(job);
    }

    private JobResponse mapToResponse(Job job) {
        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getRequiredSkills(),
                job.getLocation(),
                job.getSalaryMin(),
                job.getSalaryMax(),
                job.getJobType().name(),
                job.getExperienceRequired(),
                job.getStatus().name(),
                job.getRecruiter().getId(),
                job.getRecruiter().getName(),
                job.getRecruiter().getName(), // company name placeholder - abhi recruiter ka naam hi use kar rahe (RecruiterProfile banne pe update karenge)
                job.getCreatedAt()
        );
    }
}