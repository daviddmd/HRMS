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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final DepartmentServiceClient departmentServiceClient;
    private final JobServiceClient jobServiceClient;


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

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setIncludeHeaders(true);
        loggingFilter.setMaxPayloadLength(64000);
        return loggingFilter;
    }
}
