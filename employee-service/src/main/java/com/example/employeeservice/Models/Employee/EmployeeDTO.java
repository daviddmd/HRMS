package com.example.employeeservice.Models.Employee;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmployeeDTO extends EmployeeDTOSimple {
    private EmployeeDTOSimple manager;
}
