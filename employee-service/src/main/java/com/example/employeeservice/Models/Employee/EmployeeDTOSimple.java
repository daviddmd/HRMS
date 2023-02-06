package com.example.employeeservice.Models.Employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTOSimple {
    private Long id;
    private String name;
    private String identifier;
    private String email;
    private String phoneNumber;
}
