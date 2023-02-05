package com.example.employeeservice.Models.Contract;

import com.example.employeeservice.Enums.ContractType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractQueryDTO {
    private Long employeeId;
    private LocalDate startingDateAfter;

    private LocalDate endingDateBefore;

    private Long jobId;

    private Long departmentId;

    private BigDecimal salaryGreaterThan;

    private BigDecimal salaryLesserThan;

    private ContractType contractType;
}
