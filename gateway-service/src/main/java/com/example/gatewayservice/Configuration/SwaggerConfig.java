package com.example.gatewayservice.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI gatewayOpenAPI() {
        return new OpenAPI().info(
                new Info().
                        title("Human Resources Management System API")
                        .description("Documentation for all the Microservices in HRMS")
                        .version("v1.0")
                        .license(
                                new License().
                                        name("GNU Affero General Public License v3.0 or later").
                                        identifier("AGPL-3.0-or-later").
                                        url("https://www.gnu.org/licenses/agpl-3.0.txt")
                        )
        );
    }
}
