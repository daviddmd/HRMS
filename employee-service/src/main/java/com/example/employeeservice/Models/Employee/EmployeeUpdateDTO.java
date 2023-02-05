package com.example.employeeservice.Models.Employee;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class EmployeeUpdateDTO {
    @NotEmpty
    @NotNull
    private String name;
    @NotEmpty
    @NotNull
    private String identifier;
    @NotEmpty
    @NotNull
    private String email;
    @NotEmpty
    @NotNull
    private String phoneNumber;

    private Long managerId;
}
