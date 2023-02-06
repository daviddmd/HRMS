package com.example.jobservice.Models.Contract;

import com.example.jobservice.Models.Department.DepartmentDTO;
import com.example.jobservice.Models.Employee.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ContractDTO extends ContractDTOSimple {
    private EmployeeDTO employee;
    private DepartmentDTO department;


}
