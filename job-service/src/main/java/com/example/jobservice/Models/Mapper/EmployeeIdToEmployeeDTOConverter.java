package com.example.jobservice.Models.Mapper;

import com.example.jobservice.Clients.EmployeeServiceClient;
import com.example.jobservice.Models.Employee.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;

@RequiredArgsConstructor
public class EmployeeIdToEmployeeDTOConverter extends AbstractConverter<Long, EmployeeDTO> {
    private final EmployeeServiceClient employeeServiceClient;

    @Override
    protected EmployeeDTO convert(Long source) {
        return employeeServiceClient.getEmployee(source).getBody();
    }
}
