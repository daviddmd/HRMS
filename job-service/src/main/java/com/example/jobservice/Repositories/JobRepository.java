package com.example.jobservice.Repositories;

import com.example.jobservice.Entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
