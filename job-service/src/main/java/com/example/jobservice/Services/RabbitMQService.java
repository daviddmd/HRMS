package com.example.jobservice.Services;

import com.example.jobservice.Models.Events.DepartmentEvent;
import com.example.jobservice.Models.Events.EmployeeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQService {
    private final ContractService contractService;

    @RabbitListener(queues = "job_employee_queue_fanout")
    public void consumeMessage(EmployeeEvent event) {
        if (event.getEventType() == EmployeeEvent.EventType.DELETE) {
            Long employeeId = event.getEmployee().getId();
            contractService.deleteContractsFromEmployee(employeeId);
        }
    }

    @RabbitListener(queues = "contract_department_queue_fanout")
    public void consumeMessage(DepartmentEvent event) {
        if (event.getEventType() == DepartmentEvent.EventType.DELETE) {
            Long departmentId = event.getDepartment().getId();
            contractService.nullContractDepartments(departmentId);
        }
    }
}
