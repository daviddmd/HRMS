package com.example.jobservice.Models.Job;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class JobUpdateDTO {
    @NotBlank
    @NotNull
    private String title;
    @NotBlank
    @NotNull
    private String description;
    @NotNull
    @Min(0)
    private BigDecimal minimumSalary;

    @NotNull
    @Min(0)
    private BigDecimal maximumSalary;
    @NotBlank
    @NotNull
    private String recruiterEmail;
}
