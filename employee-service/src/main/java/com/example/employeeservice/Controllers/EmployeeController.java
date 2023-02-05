package com.example.employeeservice.Controllers;

import com.example.employeeservice.Clients.DepartmentServiceClient;
import com.example.employeeservice.Clients.JobServiceClient;
import com.example.employeeservice.Entities.Employee;
import com.example.employeeservice.Models.Employee.*;
import com.example.employeeservice.Services.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Employees")
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    //private EurekaClient eurekaClient;
    private final EmployeeService employeeService;

    private final ModelMapper mapper;
    private final DepartmentServiceClient departmentServiceClient;
    private final JobServiceClient jobServiceClient;

    @PostMapping()
    EmployeeDTO createEmployee(@RequestBody @Valid EmployeeCreateDTO dto) {
        return mapper.map(employeeService.createEmployee(dto), EmployeeDTO.class);
    }

    @GetMapping
    List<EmployeeDTOSimple> getEmployees(@RequestBody @Valid EmployeeQueryDTO dto) {
        return employeeService.searchEmployees(dto).stream().map(employee -> mapper.map(employee, EmployeeDTOSimple.class)).toList();
    }

    @GetMapping("/{id}")
    EmployeeDTO getEmployeeById(@PathVariable Long id) {
        //TODO add current departments and list of contracts
        return mapper.map(employeeService.getById(id), EmployeeDTO.class);
    }

    @PutMapping("/{id}")
    EmployeeDTOSimple updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeUpdateDTO dto) {
        Employee employee = employeeService.getById(id);
        return mapper.map(employeeService.updateEmployee(employee, dto), EmployeeDTOSimple.class);
    }

    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(employeeService.getById(id));
    }

}
