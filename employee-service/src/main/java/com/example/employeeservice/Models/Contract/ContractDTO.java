package com.example.employeeservice.Models.Contract;


import com.example.employeeservice.Enums.ContractType;
import com.example.employeeservice.Models.Jobs.JobDTO;
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
public class ContractDTO {
    private Long id;
    private LocalDate startingDate;
    private LocalDate endingDate;
    private JobDTO job;
    private BigDecimal salary;
    private ContractType contractType;


}
