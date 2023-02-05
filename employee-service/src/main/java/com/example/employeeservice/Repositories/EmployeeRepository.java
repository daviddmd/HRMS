package com.example.employeeservice.Repositories;

import com.example.employeeservice.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmailIgnoreCase(String email);

    Employee findByIdentifierIgnoreCase(String identifier);

}
