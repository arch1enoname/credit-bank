package com.arthur.calculator.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Credit Calculator Service",
                description = "Create loan offers and complete loan calculation",
                version = "1.0.0",
                contact = @Contact(
                        name = "Bulatov Arthur",
                        email = "artur132003@gmail.com",
                        url = "https://github.com/arch1enoname"
                )
        )
)
public class OpenApiConfig {
}
