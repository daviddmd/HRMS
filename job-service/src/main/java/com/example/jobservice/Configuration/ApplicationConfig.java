package com.example.jobservice.Configuration;

import com.example.jobservice.Clients.DepartmentServiceClient;
import com.example.jobservice.Clients.EmployeeServiceClient;
import com.example.jobservice.Entities.Contract;
import com.example.jobservice.Models.Contract.ContractDTO;
import com.example.jobservice.Models.Mapper.DepartmentIdToDepartmentDTOConverter;
import com.example.jobservice.Models.Mapper.EmployeeIdToEmployeeDTOConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final EmployeeServiceClient employeeServiceClient;
    private final DepartmentServiceClient departmentServiceClient;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.typeMap(Contract.class, ContractDTO.class).addMappings(
                new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(new EmployeeIdToEmployeeDTOConverter(employeeServiceClient)).map(source.getEmployeeId(), destination.getEmployee());
                        using(new DepartmentIdToDepartmentDTOConverter(departmentServiceClient)).map(source.getDepartmentId(), destination.getDepartment());
                    }
                }
        );
        return modelMapper;
    }
}
