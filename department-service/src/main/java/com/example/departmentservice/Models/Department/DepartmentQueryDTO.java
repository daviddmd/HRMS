package com.example.departmentservice.Models.Department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DepartmentQueryDTO {
    private Long employeeId;
    private String code;
    private Long locationId;
}
