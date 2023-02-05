package com.example.jobservice.Controllers;

import com.example.jobservice.Models.Job.JobCreateDTO;
import com.example.jobservice.Models.Job.JobDTO;
import com.example.jobservice.Models.Job.JobUpdateDTO;
import com.example.jobservice.Services.JobService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Jobs")
@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;
    private final ModelMapper mapper;

    @PostMapping
    JobDTO createJob(@RequestBody @Valid JobCreateDTO dto) {
        return mapper.map(jobService.createJob(dto), JobDTO.class);
    }

    @GetMapping
    List<JobDTO> getJobs() {
        return jobService.getJobs().stream().map(job -> mapper.map(job, JobDTO.class)).toList();
    }

    @GetMapping("/{id}")
    JobDTO getJob(@PathVariable Long id) {
        return mapper.map(jobService.getById(id), JobDTO.class);
    }

    @PutMapping("/{id}")
    JobDTO updateJob(@PathVariable Long id, @RequestBody @Valid JobUpdateDTO dto) {
        return mapper.map(jobService.updateJob(jobService.getById(id), dto), JobDTO.class);
    }

    @DeleteMapping("/{id}")
    void deleteJob(@PathVariable Long id) {
        jobService.deleteJob(jobService.getById(id));
    }

}
