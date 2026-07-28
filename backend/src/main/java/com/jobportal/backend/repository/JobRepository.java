package com.jobportal.backend.repository;

import com.jobportal.backend.entity.Job;
import com.jobportal.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByRecruiter(User recruiter);

    Page<Job> findByStatus(Job.JobStatus status, Pageable pageable);

    Page<Job> findByLocationContainingIgnoreCaseAndStatus(String location, Job.JobStatus status, Pageable pageable);
}
