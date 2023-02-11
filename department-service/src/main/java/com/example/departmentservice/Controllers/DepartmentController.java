package com.example.departmentservice.Controllers;

import com.example.departmentservice.Entities.Department;
import com.example.departmentservice.Models.Department.*;
import com.example.departmentservice.Models.Events.DepartmentEvent;
import com.example.departmentservice.Services.DepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.departmentservice.Configuration.RabbitMQConfig.departmentExchange;

@Tag(name = "Departments")
@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;
    private final ModelMapper mapper;

    private final AmqpTemplate amqpTemplate;

    @PostMapping
    @RolesAllowed("administration")
    DepartmentDTO createDepartment(@RequestBody @Valid DepartmentCreateDTO dto) {
        return mapper.map(departmentService.createDepartment(dto), DepartmentDTO.class);
    }

    @GetMapping
    @RolesAllowed({"administration", "human-resources"})
    List<DepartmentDTO> getDepartments(@RequestBody @Valid DepartmentQueryDTO dto) {
        return departmentService.queryDepartments(dto).stream().map(department -> mapper.map(department, DepartmentDTO.class)).toList();
    }

    @GetMapping("/employee/{id}")
    @RolesAllowed({"administration", "human-resources"})
    List<DepartmentDTOSimple> getDepartmentsEmployee(@PathVariable Long id) {
        return departmentService.queryDepartmentsEmployee(id).stream().map(department -> mapper.map(department, DepartmentDTOSimple.class)).toList();
    }

    @GetMapping("/{id}")
    @RolesAllowed({"administration", "human-resources"})
    DepartmentDTO getDepartment(@PathVariable Long id) {
        return mapper.map(departmentService.getById(id), DepartmentDTO.class);
    }

    @GetMapping("/{id}/simple")
    @RolesAllowed({"administration", "human-resources"})
    DepartmentDTOSimple getDepartmentSimple(@PathVariable Long id) {
        return mapper.map(departmentService.getById(id), DepartmentDTOSimple.class);
    }

    @PutMapping("/{id}")
    @RolesAllowed({"administration"})
    DepartmentDTO updateDepartment(@PathVariable Long id, @RequestBody @Valid DepartmentUpdateDTO dto) {
        return mapper.map(departmentService.updateDepartment(departmentService.getById(id), dto), DepartmentDTO.class);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"administration"})
    void deleteDepartment(@PathVariable Long id) {
        Department department = departmentService.getById(id);
        amqpTemplate.convertAndSend(
                departmentExchange, "",
                DepartmentEvent.builder().
                        department(mapper.map(department, DepartmentDTOSimple.class)).
                        eventType(DepartmentEvent.EventType.DELETE).
                        build()
        );
        departmentService.deleteDepartment(department);
    }
}
