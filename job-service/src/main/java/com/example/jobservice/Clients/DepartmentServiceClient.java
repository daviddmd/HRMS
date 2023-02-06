package com.example.jobservice.Clients;

import com.example.jobservice.Models.Department.DepartmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "department-service", dismiss404 = true)
public interface DepartmentServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/departments/{id}/simple")
    ResponseEntity<DepartmentDTO> getDepartment(@PathVariable Long id);
}
