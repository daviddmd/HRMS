package com.example.departmentservice.Models.Events;

import com.example.departmentservice.Models.Department.DepartmentDTOSimple;
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

    private DepartmentDTOSimple department;
    private EventType eventType;
}
