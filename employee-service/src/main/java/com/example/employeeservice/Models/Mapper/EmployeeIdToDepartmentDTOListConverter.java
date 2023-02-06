package com.example.employeeservice.Models.Mapper;

import com.example.employeeservice.Clients.DepartmentServiceClient;
import com.example.employeeservice.Models.Department.DepartmentDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;

import java.util.List;

@RequiredArgsConstructor
public class EmployeeIdToDepartmentDTOListConverter extends AbstractConverter<Long, List<DepartmentDTO>> {
    private final DepartmentServiceClient departmentServiceClient;

    @Override
    protected List<DepartmentDTO> convert(Long source) {
        return departmentServiceClient.getDepartments(source);
    }
}
