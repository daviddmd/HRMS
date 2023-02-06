package com.example.employeeservice.Configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public final static String employeeExchange = "employee_exchange";
    private final static String departmentQueue = "department_employee_queue_fanout";
    private final static String jobQueue = "job_employee_queue_fanout";


    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange(employeeExchange);
    }

    @Bean
    Queue departmentQueue() {
        return new Queue(departmentQueue, true);
    }

    @Bean
    Queue jobQueue() {
        return new Queue(jobQueue, true);
    }

    @Bean
    Binding departmentBinding(Queue departmentQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(departmentQueue).to(exchange);
    }

    @Bean
    Binding jobBinding(Queue jobQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(jobQueue).to(exchange);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
