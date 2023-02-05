package com.example.jobservice.Models.Contract;

import com.example.jobservice.Enums.ContractType;
import com.example.jobservice.Models.Department.DepartmentDTO;
import com.example.jobservice.Models.Employee.EmployeeDTO;
import com.example.jobservice.Models.Job.JobDTO;
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
public class ContractDTO {
    private Long id;
    private EmployeeDTO employee;
    private LocalDate startingDate;
    private LocalDate endingDate;
    private JobDTO job;

    private DepartmentDTO department;
    private BigDecimal salary;
    private ContractType contractType;


}
