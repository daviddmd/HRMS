package com.example.departmentservice.Models.Department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class DepartmentUpdateDTO {
    @NotNull
    @NotBlank
    private String code;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;
    @NotNull
    private List<Long> employees;
    @NotNull
    private Long locationId;
}
