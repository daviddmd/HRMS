package com.example.employeeservice.Services;

import com.example.employeeservice.Entities.Employee;
import com.example.employeeservice.Exceptions.CustomException;
import com.example.employeeservice.Models.Employee.EmployeeCreateDTO;
import com.example.employeeservice.Models.Employee.EmployeeQueryDTO;
import com.example.employeeservice.Models.Employee.EmployeeUpdateDTO;
import com.example.employeeservice.Repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Employee getById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new CustomException(String.format("No Employee with ID %d", id), HttpStatus.NOT_FOUND));
    }

    public Employee createEmployee(EmployeeCreateDTO dto) {
        Employee manager = null;
        if (dto.getManagerId() != null) {
            manager = getById(dto.getManagerId());
        }
        if (employeeRepository.findByEmailIgnoreCase(dto.getEmail()) != null) {
            throw new CustomException(String.format("An employee with email %s already exists", dto.getEmail()), HttpStatus.BAD_REQUEST);
        }
        if (employeeRepository.findByIdentifierIgnoreCase(dto.getIdentifier()) != null) {
            throw new CustomException(String.format("An employee with identifier %s already exists", dto.getIdentifier()), HttpStatus.BAD_REQUEST);
        }
        Employee employee = Employee.builder().
                name(dto.getName()).
                email(dto.getEmail()).
                phoneNumber(dto.getPhoneNumber()).
                identifier(dto.getIdentifier()).
                manager(manager).
                build();
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Employee employee, EmployeeUpdateDTO dto) {
        if (dto.getManagerId() != null && dto.getManagerId().equals(employee.getId())) {
            throw new CustomException(
                    String.format("Invalid manager ID (Same ID: %d as the employee: %d).", dto.getManagerId(), employee.getId()),
                    HttpStatus.BAD_REQUEST
            );
        }
        Employee manager = dto.getManagerId() == null ? null : getById(dto.getManagerId());
        if (!dto.getEmail().equals(employee.getEmail()) && employeeRepository.findByEmailIgnoreCase(dto.getEmail()) != null) {
            throw new CustomException(String.format("An employee with email %s already exists", dto.getEmail()), HttpStatus.BAD_REQUEST);
        }
        if (!dto.getIdentifier().equals(employee.getIdentifier()) && employeeRepository.findByIdentifierIgnoreCase(dto.getIdentifier()) != null) {
            throw new CustomException(String.format("An employee with identifier %s already exists", dto.getIdentifier()), HttpStatus.BAD_REQUEST);
        }
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setIdentifier(dto.getIdentifier());
        employee.setManager(manager);
        return employeeRepository.save(employee);
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> searchEmployees(EmployeeQueryDTO dto) {
        Stream<Employee> employees = getEmployees().stream();
        if (dto.getManagerId() != null) {
            employees = employees.filter(employee -> employee.getManager() != null && employee.getManager().getId().equals(dto.getManagerId()));
        }
        if (dto.getName() != null) {
            employees = employees.filter(employee -> employee.getName().toUpperCase().contains(dto.getName().toUpperCase()));
        }
        if (dto.getIdentifier() != null) {
            employees = employees.filter(employee -> employee.getIdentifier().equals(dto.getIdentifier()));
        }
        if (dto.getPhoneNumber() != null) {
            String phoneNumber = dto.getPhoneNumber().replaceAll("\\D+", "");
            employees = employees.filter(employee -> employee.getPhoneNumber().replaceAll("\\D+", "").contains(phoneNumber));
        }
        if (dto.getEmail() != null) {
            employees = employees.filter(employee -> employee.getEmail().equals(dto.getEmail()));
        }
        return employees.toList();
    }

    public void deleteEmployee(Employee employee) {
        //TODO cascade operations with department_users and employee_contracts; ALT: MQ
        for (Employee e : getEmployees()) {
            if (e.getManager() != null && e.getManager().getId().equals(employee.getId())) {
                e.setManager(null);
            }
        }
        employeeRepository.delete(employee);
    }

}
