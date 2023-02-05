package com.example.employeeservice.Configuration;

import com.example.employeeservice.Clients.DepartmentServiceClient;
import com.example.employeeservice.Clients.JobServiceClient;
import com.example.employeeservice.Entities.Employee;
import com.example.employeeservice.Models.Employee.EmployeeDTO;
import com.example.employeeservice.Models.Mapper.EmployeeIdToContractDTOListConverter;
import com.example.employeeservice.Models.Mapper.EmployeeIdToDepartmentDTOListConverter;
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
    private final DepartmentServiceClient departmentServiceClient;
    private final JobServiceClient jobServiceClient;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.typeMap(Employee.class, EmployeeDTO.class).addMappings(
                new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(new EmployeeIdToContractDTOListConverter(jobServiceClient)).map(source.getId(), destination.getContracts());
                        using(new EmployeeIdToDepartmentDTOListConverter(departmentServiceClient)).map(source.getId(), destination.getDepartments());
                    }
                }
        );
        return modelMapper;
    }
}
