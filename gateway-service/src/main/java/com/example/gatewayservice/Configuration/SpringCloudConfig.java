package com.example.gatewayservice.Configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/employees/**")
                        .uri("lb://employee-service")
                )

                .route(r -> r.path("/departments/**", "/locations/**")
                        .uri("lb://department-service")
                )
                .route(r -> r.path("/jobs/**", "/contracts/**")
                        .uri("lb://job-service")
                )
                .build();
    }
}
