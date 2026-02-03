package com.docencia.pgv.noticias.rest;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  OpenAPI api() {
    return new OpenAPI()
        .info(new Info()
            .title("Noticias API (sin seguridad)")
            .version("1.0.0"));
  }
}
