package com.example.gatewayservice.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static java.lang.String.format;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SpringCloudConfig {
    @Value("${management.endpoints.web.base-path}")
    private String actuatorPath;
    @Value("${springdoc.swagger-ui.path}")
    private String swaggerPath;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, format("%s/**", actuatorPath)).permitAll()
                .pathMatchers(HttpMethod.GET, "/webjars/**").permitAll()
                .pathMatchers(HttpMethod.GET, format("%s/**", swaggerPath)).permitAll()
                .pathMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
                .pathMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/employees/v3/api-docs/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/departments/v3/api-docs/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/jobs/v3/api-docs/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/employees/actuator/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/departments/actuator/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/jobs/actuator/**").permitAll()
                .anyExchange().
                authenticated()
                .and()
                .csrf().disable()
                .oauth2Login()
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }
}
