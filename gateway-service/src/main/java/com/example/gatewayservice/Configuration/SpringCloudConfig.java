package com.example.gatewayservice.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SpringCloudConfig {
    /*
    @Autowired
    private TokenRelayGatewayFilterFactory filterFactory;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/employees/**")
                        .filters(f -> f.filter(filterFactory.apply()))
                        .uri("lb://employee-service")
                )

                .route(r -> r.path("/departments/**", "/locations/**")
                        .filters(f -> f.filter(filterFactory.apply()))
                        .uri("lb://department-service")
                )
                .route(r -> r.path("/jobs/**", "/contracts/**")
                        .filters(f -> f.filter(filterFactory.apply()))
                        .uri("lb://job-service")
                )
                .build();
    }
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
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
