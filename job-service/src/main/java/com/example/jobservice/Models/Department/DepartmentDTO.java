package com.example.jobservice.Models.Department;

import com.example.jobservice.Models.Location.LocationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private LocationDTO location;
}
