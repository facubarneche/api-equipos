package equipos.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class swaggerConfig {


    @Configuration
    public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI() {
            return new OpenAPI()
                    .info(new Info()
                            .title("API Equipos")
                            .version("1.0")
                            .description("Documentación generada con Swagger + Springdoc"));
        }
    }

}
