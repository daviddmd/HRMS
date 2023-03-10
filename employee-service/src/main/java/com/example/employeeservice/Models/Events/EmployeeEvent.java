package com.example.employeeservice.Models.Events;

import com.example.employeeservice.Models.Employee.EmployeeDTOSimple;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeEvent {
    private EmployeeDTOSimple employee;
    private EventType eventType;

    public enum EventType {
        CREATE,
        UPDATE,
        DELETE
    }
}
