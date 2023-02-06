package com.example.departmentservice.Models.Department;

import com.example.departmentservice.Models.Employee.EmployeeDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DepartmentDTO extends DepartmentDTOSimple {
    private List<EmployeeDTO> employees;

}
