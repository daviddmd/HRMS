package com.example.departmentservice.Controllers;

import com.example.departmentservice.Models.Department.*;
import com.example.departmentservice.Services.DepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Departments")
@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;
    private final ModelMapper mapper;

    @PostMapping
    DepartmentDTO createDepartment(@RequestBody @Valid DepartmentCreateDTO dto) {
        return mapper.map(departmentService.createDepartment(dto), DepartmentDTO.class);
    }

    @GetMapping
    List<DepartmentDTO> getDepartments(@RequestBody @Valid DepartmentQueryDTO dto) {
        return departmentService.queryDepartments(dto).stream().map(department -> mapper.map(department, DepartmentDTO.class)).toList();
    }

    @GetMapping("/employee/{id}")
    List<DepartmentDTOSimple> getDepartmentsEmployee(@PathVariable Long id) {
        return departmentService.queryDepartments(DepartmentQueryDTO.builder().employeeId(id).build()).stream().map(department -> mapper.map(department, DepartmentDTOSimple.class)).toList();
    }

    @GetMapping("/{id}")
    DepartmentDTO getDepartment(@PathVariable Long id) {
        return mapper.map(departmentService.getById(id), DepartmentDTO.class);
    }

    @PutMapping("/{id}")
    DepartmentDTO updateDepartment(@PathVariable Long id, @RequestBody @Valid DepartmentUpdateDTO dto) {
        return mapper.map(departmentService.updateDepartment(departmentService.getById(id), dto), DepartmentDTO.class);
    }

    @DeleteMapping("/{id}")
    void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(departmentService.getById(id));
    }
}
