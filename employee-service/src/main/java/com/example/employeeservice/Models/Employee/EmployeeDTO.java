package com.example.employeeservice.Models.Employee;

import com.example.employeeservice.Models.Contract.ContractDTO;
import com.example.employeeservice.Models.Department.DepartmentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmployeeDTO extends EmployeeDTOSimple {
    private EmployeeDTOSimple manager;

    private List<DepartmentDTO> departments;

    private List<ContractDTO> contracts;
}
