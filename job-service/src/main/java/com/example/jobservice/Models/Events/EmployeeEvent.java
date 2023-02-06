package com.example.jobservice.Models.Events;

import com.example.jobservice.Models.Employee.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeEvent {
    public enum EventType {
        CREATE,
        UPDATE,
        DELETE
    }

    private EmployeeDTO employee;
    private EventType eventType;
}
