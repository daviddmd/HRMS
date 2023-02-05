package com.example.departmentservice.Configuration;

import com.example.departmentservice.Clients.EmployeeServiceClient;
import com.example.departmentservice.Entities.Department;
import com.example.departmentservice.Models.Department.DepartmentDTO;
import com.example.departmentservice.Models.Mapper.EmployeeIdToEmployeeDTOConverter;
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

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.typeMap(Department.class, DepartmentDTO.class).addMappings(
                new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(new EmployeeIdToEmployeeDTOConverter(employeeServiceClient)).map(source.getEmployees(), destination.getEmployees());
                    }
                }
        );
        return modelMapper;
    }
}
