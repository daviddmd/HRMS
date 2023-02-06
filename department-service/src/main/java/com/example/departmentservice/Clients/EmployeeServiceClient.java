package com.example.departmentservice.Clients;


import com.example.departmentservice.Models.Employee.EmployeeDTO;
import com.example.departmentservice.Models.Employee.EmployeeQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "employee-service", dismiss404 = true)
public interface EmployeeServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/employees/{id}/simple")
    ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id);

}
