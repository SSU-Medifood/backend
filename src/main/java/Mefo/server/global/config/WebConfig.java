package Mefo.server.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
<<<<<<< HEAD
                .allowedOrigins("http://localhost:5173","https://mefoweb.com")
=======
                .allowedOrigins("http://localhost:5173" ,"https://mefoweb.com")
>>>>>>> 54b6040 (Fix: EC2에서 수정한 설정 파일 업데이트)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false);
    }
}
