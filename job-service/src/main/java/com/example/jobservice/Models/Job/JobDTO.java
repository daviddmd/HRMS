package com.example.jobservice.Models.Job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobDTO {
    private Long id;
    private String title;
    private String description;
    private BigDecimal minimumSalary;
    private BigDecimal maximumSalary;
    private String recruiterEmail;

}
