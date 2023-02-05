package com.example.employeeservice.Controllers;

import com.example.employeeservice.Entities.Employee;
import com.example.employeeservice.Models.Employee.EmployeeCreateDTO;
import com.example.employeeservice.Models.Employee.EmployeeDTO;
import com.example.employeeservice.Models.Employee.EmployeeSearchDTO;
import com.example.employeeservice.Models.Employee.EmployeeUpdateDTO;
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

    @PostMapping()
    EmployeeDTO createEmployee(@RequestBody @Valid EmployeeCreateDTO dto) {
        return mapper.map(employeeService.createEmployee(dto), EmployeeDTO.class);
    }

    @GetMapping
    List<EmployeeDTO> getEmployees(@RequestBody @Valid EmployeeSearchDTO dto) {
        return employeeService.searchEmployees(dto).stream().map(employee -> mapper.map(employee, EmployeeDTO.class)).toList();
    }

    @GetMapping("/{id}")
    EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return mapper.map(employeeService.getById(id), EmployeeDTO.class);
    }

    @PutMapping("/{id}")
    EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeUpdateDTO dto) {
        Employee employee = employeeService.getById(id);
        return mapper.map(employeeService.updateEmployee(employee, dto), EmployeeDTO.class);
    }

    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(employeeService.getById(id));
    }

}
