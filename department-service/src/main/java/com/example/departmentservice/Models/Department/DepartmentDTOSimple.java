package com.example.departmentservice.Models.Department;

import com.example.departmentservice.Models.Location.LocationDTOSimple;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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

    private int numberEmployees;


}
