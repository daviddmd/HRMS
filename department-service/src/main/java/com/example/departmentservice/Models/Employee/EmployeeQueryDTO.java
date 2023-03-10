package com.example.departmentservice.Models.Employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeQueryDTO {
    private Long managerId;
    private String name;
    private String email;
    private String phoneNumber;
    private String identifier;
}
