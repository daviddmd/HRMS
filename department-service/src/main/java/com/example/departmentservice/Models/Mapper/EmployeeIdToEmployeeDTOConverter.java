package com.example.departmentservice.Models.Mapper;

import com.example.departmentservice.Clients.EmployeeServiceClient;
import com.example.departmentservice.Models.Employee.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;

import java.util.List;

@RequiredArgsConstructor
public class EmployeeIdToEmployeeDTOConverter extends AbstractConverter<List<Long>, List<EmployeeDTO>> {
    private final EmployeeServiceClient employeeServiceClient;

    @Override
    protected List<EmployeeDTO> convert(List<Long> source) {
        return source.stream().map(employeeId -> employeeServiceClient.getEmployee(employeeId).getBody()).toList();
    }
}
