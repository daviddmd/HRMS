package com.example.employeeservice.Clients;

import com.example.employeeservice.Models.Department.DepartmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("department-service")
public interface DepartmentServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/departments/employee/{id}")
    List<DepartmentDTO> getDepartments(@PathVariable Long id);
}
