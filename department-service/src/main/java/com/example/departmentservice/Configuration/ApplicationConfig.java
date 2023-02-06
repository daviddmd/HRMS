package com.example.departmentservice.Configuration;

import com.example.departmentservice.Clients.EmployeeServiceClient;
import com.example.departmentservice.Entities.Department;
import com.example.departmentservice.Models.Department.DepartmentDTO;
import com.example.departmentservice.Models.Department.DepartmentDTOSimple;
import com.example.departmentservice.Models.Mapper.EmployeeIdToEmployeeDTOConverter;
import com.example.departmentservice.Models.Mapper.EmployeeListToNumberEmployeesConverter;
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
    private final EmployeeServiceClient employeeServiceClient;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.typeMap(Department.class, DepartmentDTO.class).addMappings(
                new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(new EmployeeIdToEmployeeDTOConverter(employeeServiceClient)).map(source.getEmployees(), destination.getEmployees());
                        using(new EmployeeListToNumberEmployeesConverter()).map(source.getEmployees(), destination.getNumberEmployees());
                    }
                }
        );
        modelMapper.typeMap(Department.class, DepartmentDTOSimple.class).addMappings(
                new PropertyMap<Department, DepartmentDTOSimple>() {
                    @Override
                    protected void configure() {
                        using(new EmployeeListToNumberEmployeesConverter()).map(source.getEmployees(), destination.getNumberEmployees());
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
