package com.example.employeeservice.Controllers;

import com.example.employeeservice.Entities.Employee;
import com.example.employeeservice.Models.Employee.*;
import com.example.employeeservice.Models.Events.EmployeeEvent;
import com.example.employeeservice.Services.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.employeeservice.Configuration.RabbitMQConfig.userExchange;

@Tag(name = "Employees")
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final ModelMapper mapper;

    private final AmqpTemplate amqpTemplate;

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
        return mapper.map(employeeService.getById(id), EmployeeDTO.class);
    }


    @GetMapping("/{id}/simple")
    EmployeeDTOSimple getEmployeeByIdSimple(@PathVariable Long id) {
        return mapper.map(employeeService.getById(id), EmployeeDTOSimple.class);
    }

    @PutMapping("/{id}")
    EmployeeDTOSimple updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeUpdateDTO dto) {
        Employee employee = employeeService.getById(id);
        return mapper.map(employeeService.updateEmployee(employee, dto), EmployeeDTOSimple.class);
    }

    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        amqpTemplate.convertAndSend(
                userExchange, "",
                EmployeeEvent.builder().
                        employeeDTO(mapper.map(employee, EmployeeDTOSimple.class)).
                        eventType(EmployeeEvent.EventType.DELETE).
                        build()
        );
        employeeService.deleteEmployee(employeeService.getById(id));
    }

}
