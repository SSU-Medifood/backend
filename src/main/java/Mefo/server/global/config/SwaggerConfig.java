package Mefo.server.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    private final String JWT = "JWT";
    private final String BEARER = "Bearer";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(getInfo())
                .servers(getServers())
                .components(getComponents())
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"));
    }

    private List<Server> getServers() {
        return List.of(
                new Server().url("https://api.mefoweb.com").description("배포 서버"),
                new Server().url("http://localhost:8080").description("로컬 서버")
        );
    }
    private Info getInfo() {
        return new Info()
                .title("Mefo API") // API의 제목
                .description("Mefo api 명세서") // API에 대한 설명
                .version("dev"); // API의 버전
    }

    private Components getComponents() {
        return new Components().addSecuritySchemes("BearerAuth", new SecurityScheme()
                .name("Authorization")
                .scheme(BEARER)
                .bearerFormat(JWT)
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.HTTP)
        );
    }
}
