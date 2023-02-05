package com.example.jobservice.Models.Mapper;

import com.example.jobservice.Clients.DepartmentServiceClient;
import com.example.jobservice.Models.Department.DepartmentDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;

@RequiredArgsConstructor

public class DepartmentIdToDepartmentDTOConverter extends AbstractConverter<Long, DepartmentDTO> {
    private final DepartmentServiceClient departmentServiceClient;

    @Override
    protected DepartmentDTO convert(Long source) {
        return departmentServiceClient.getDepartment(source).getBody();
    }
}
