package com.example.departmentservice.Configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public final static String departmentExchange = "department_exchange";
    private final static String contractQueue = "contract_department_queue_fanout";

    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange(departmentExchange);
    }

    @Bean
    Queue contractQueue() {
        return new Queue(contractQueue, true);
    }

    @Bean
    Binding contractBinding(Queue contractQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(contractQueue).to(exchange);
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
