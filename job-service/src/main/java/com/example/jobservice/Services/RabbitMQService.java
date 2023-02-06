package com.example.jobservice.Services;

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
            Long employeeId = event.getEmployeeDTO().getId();
            contractService.deleteContractsFromEmployee(employeeId);
        }
    }
}
