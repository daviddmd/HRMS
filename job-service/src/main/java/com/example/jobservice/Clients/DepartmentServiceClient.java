package com.example.jobservice.Clients;

import com.example.jobservice.Models.Department.DepartmentDTO;
import com.example.jobservice.Models.Department.DepartmentQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "department-service", dismiss404 = true)
public interface DepartmentServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/departments/{id}")
    ResponseEntity<DepartmentDTO> getDepartment(@PathVariable Long id);

    @RequestMapping(method = RequestMethod.GET, value = "/departments", consumes = "application/json")
    ResponseEntity<List<DepartmentDTO>> getDepartments(DepartmentQueryDTO dto);
}
