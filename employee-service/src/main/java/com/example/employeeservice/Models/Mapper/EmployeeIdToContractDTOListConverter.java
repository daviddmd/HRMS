package com.example.employeeservice.Models.Mapper;

import com.example.employeeservice.Clients.JobServiceClient;
import com.example.employeeservice.Models.Contract.ContractDTO;
import com.example.employeeservice.Models.Contract.ContractQueryDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;

import java.util.List;

@RequiredArgsConstructor
public class EmployeeIdToContractDTOListConverter extends AbstractConverter<Long, List<ContractDTO>> {
    private final JobServiceClient jobServiceClient;

    @Override
    protected List<ContractDTO> convert(Long source) {
        return jobServiceClient.getContracts(ContractQueryDTO.builder().employeeId(source).build());
    }
}
