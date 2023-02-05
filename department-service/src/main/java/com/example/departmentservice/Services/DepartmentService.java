package com.example.departmentservice.Services;

import com.example.departmentservice.Clients.EmployeeServiceClient;
import com.example.departmentservice.Entities.Department;
import com.example.departmentservice.Entities.Location;
import com.example.departmentservice.Exceptions.CustomException;
import com.example.departmentservice.Models.Department.DepartmentCreateDTO;
import com.example.departmentservice.Models.Department.DepartmentQueryDTO;
import com.example.departmentservice.Models.Department.DepartmentUpdateDTO;
import com.example.departmentservice.Models.Employee.EmployeeDTO;
import com.example.departmentservice.Repositories.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final LocationService locationService;

    private final EmployeeServiceClient employeeServiceClient;

    public Department getById(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new CustomException(String.format("No Department with ID %d", id), HttpStatus.NOT_FOUND));
    }

    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    public List<Department> queryDepartments(DepartmentQueryDTO dto) {
        Stream<Department> departmentStream = getAll().stream();
        if (dto.getEmployeeId() != null) {
            departmentStream = departmentStream.filter(department -> department.getEmployees().contains(dto.getEmployeeId()));
        }
        if (dto.getCode() != null) {
            departmentStream = departmentStream.filter(department -> department.getCode().equals(dto.getCode()));
        }
        if (dto.getLocationId() != null) {
            departmentStream = departmentStream.filter(department -> department.getLocation().getId().equals(dto.getLocationId()));
        }
        return departmentStream.toList();
    }

    public Department createDepartment(DepartmentCreateDTO dto) {
        if (departmentRepository.existsByCodeIgnoreCase(dto.getCode())) {
            throw new CustomException(String.format("A department with Code %s already exists.", dto.getCode()), HttpStatus.BAD_REQUEST);
        }
        Location location = locationService.getById(dto.getLocationId());
        validateEmployeeList(dto.getEmployees());
        Department department = Department.builder().
                code(dto.getCode()).
                name(dto.getName()).
                description(dto.getDescription()).
                employees(dto.getEmployees()).
                location(location).
                build();
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Department department, DepartmentUpdateDTO dto) {
        if (!department.getCode().equals(dto.getCode()) && departmentRepository.existsByCodeIgnoreCase(dto.getCode())) {
            throw new CustomException(String.format("A department with Code %s already exists.", dto.getCode()), HttpStatus.BAD_REQUEST);
        }
        Location location = locationService.getById(dto.getLocationId());
        validateEmployeeList(dto.getEmployees());
        department.setCode(dto.getCode());
        department.setName(dto.getName());
        department.setDescription(dto.getDescription());
        department.setEmployees(dto.getEmployees());
        department.setLocation(location);
        return departmentRepository.save(department);
    }

    public void deleteDepartment(Department department) {
        departmentRepository.delete(department);
    }

    private void validateEmployeeList(List<Long> employees) {
        ResponseEntity<EmployeeDTO> responseEntity;
        for (Long employeeId : employees) {
            responseEntity = employeeServiceClient.getEmployee(employeeId);
            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                throw new CustomException(String.format("Employee with ID %d doesn't exist.", employeeId), HttpStatus.NOT_FOUND);
            }
        }
    }
}
