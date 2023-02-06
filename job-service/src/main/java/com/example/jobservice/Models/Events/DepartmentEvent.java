package com.example.jobservice.Models.Events;

import com.example.jobservice.Models.Department.DepartmentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentEvent {
    public enum EventType {
        CREATE,
        UPDATE,
        DELETE
    }

    private DepartmentDTO department;
    private EventType eventType;
}
