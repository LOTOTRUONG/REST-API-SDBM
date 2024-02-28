package vn.loto.rest01.ressource;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
@OpenAPIDefinition(info =
        @Info(
                title = "API de le SDBM",
                version = "1.0",
                description = "SDBM API",
                contact = @Contact(url = "https://lototruong.github.io/", name = "SDBM", email = "loan.truongtt@gmail.com")
        ))
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SDBMApplication extends Application {

}