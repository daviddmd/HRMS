package com.example.departmentservice.Services;

import com.example.departmentservice.Entities.Department;
import com.example.departmentservice.Models.Events.EmployeeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RabbitMQService {
    private final DepartmentService departmentService;

    @RabbitListener(queues = "department_employee_queue_fanout")
    public void consumeMessage(EmployeeEvent event) {
        if (event.getEventType() == EmployeeEvent.EventType.DELETE) {
            Long employeeId = event.getEmployeeDTO().getId();
            List<Department> employeeDepartmentList = departmentService.queryDepartmentsEmployee(employeeId);
            employeeDepartmentList.forEach(department -> departmentService.removeEmployeeFromDepartment(department, employeeId));
        }
    }
}
