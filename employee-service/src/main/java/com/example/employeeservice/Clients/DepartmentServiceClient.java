package com.example.employeeservice.Clients;

import com.example.employeeservice.Models.Contract.ContractDTO;
import com.example.employeeservice.Models.Department.DepartmentQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("department-service")
public interface DepartmentServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/departments", consumes = "application/json")
    List<ContractDTO> getDepartments(DepartmentQueryDTO dto);
}
