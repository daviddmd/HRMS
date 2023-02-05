package com.example.jobservice.Services;

import com.example.jobservice.Entities.Job;
import com.example.jobservice.Exceptions.CustomException;
import com.example.jobservice.Models.Job.JobCreateDTO;
import com.example.jobservice.Models.Job.JobUpdateDTO;
import com.example.jobservice.Repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;


    public Job getById(Long id) {
        return jobRepository.findById(id).orElseThrow(() -> new CustomException(String.format("No Job with ID %d", id), HttpStatus.NOT_FOUND));
    }

    public Job createJob(JobCreateDTO dto) {
        if (dto.getMaximumSalary().compareTo(dto.getMinimumSalary()) < 0) {
            throw new CustomException("The maximum salary is lesser than the minimum salary", HttpStatus.BAD_REQUEST);
        }
        Job job = Job.builder().
                title(dto.getTitle()).
                description(dto.getDescription()).
                minimumSalary(dto.getMinimumSalary().setScale(2, RoundingMode.HALF_UP)).
                maximumSalary(dto.getMaximumSalary().setScale(2, RoundingMode.HALF_UP)).
                recruiterEmail(dto.getRecruiterEmail()).
                contracts(List.of()).
                build();
        return jobRepository.save(job);
    }

    public Job updateJob(Job job, JobUpdateDTO dto) {
        if (dto.getMaximumSalary().compareTo(dto.getMinimumSalary()) < 0) {
            throw new CustomException("The maximum salary is lesser than the minimum salary", HttpStatus.BAD_REQUEST);
        }
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setMinimumSalary(dto.getMinimumSalary().setScale(2, RoundingMode.HALF_UP));
        job.setMaximumSalary(dto.getMaximumSalary().setScale(2, RoundingMode.HALF_UP));
        job.setRecruiterEmail(dto.getRecruiterEmail());
        return jobRepository.save(job);
    }

    public void deleteJob(Job job) {
        /*
        for (Contract contract : contractService.getContracts()) {
            if (contract.getJob().equals(job)) {
                contractService.deleteContract(contract);
            }
        }
         */
        jobRepository.delete(job);
    }

    public List<Job> getJobs() {
        return jobRepository.findAll();
    }
}
