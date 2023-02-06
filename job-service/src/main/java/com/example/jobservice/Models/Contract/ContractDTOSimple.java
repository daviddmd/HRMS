package com.example.jobservice.Models.Contract;

import com.example.jobservice.Enums.ContractType;
import com.example.jobservice.Models.Job.JobDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ContractDTOSimple {
    private Long id;
    private LocalDate startingDate;
    private LocalDate endingDate;
    private JobDTO job;
    private BigDecimal salary;
    private ContractType contractType;


}
