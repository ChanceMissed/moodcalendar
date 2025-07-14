package com.example.moodcalendar.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(){

        return new OpenAPI()
            .info(new Info()
                .title("Mood Calendar API")
                .description("Mood Calendar API Documentation")
                .version("v1.0.0"))
            .servers(
                List.of(
                    new Server()
                        .url("http://localhost:8090")
                        .description("Local Server")
                ));

    }

}
