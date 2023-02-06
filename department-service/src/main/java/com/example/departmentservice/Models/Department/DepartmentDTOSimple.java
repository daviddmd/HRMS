package com.example.departmentservice.Models.Department;

import com.example.departmentservice.Models.Employee.EmployeeDTO;
import com.example.departmentservice.Models.Location.LocationDTOSimple;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DepartmentDTOSimple {
    private Long id;
    private String code;
    private String name;
    private String description;
    private LocationDTOSimple location;


}
