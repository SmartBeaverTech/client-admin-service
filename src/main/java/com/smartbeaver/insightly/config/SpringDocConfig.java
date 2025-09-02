package com.smartbeaver.insightly.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI insightlyOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Client Admin Service API")
                        .description("API documentation for Insightly Client Registration & Management")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("SmartBeaver Support")
                                .email("support@smartbeaver.com")
                                .url("https://smartbeaver.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Project Wiki")
                        .url("https://github.com/smartbeaver/insightly/wiki"));
    }
}

