package me.dio.parking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Doc OpenAPI
 *
 * @see <a href='https://springdoc.org/'>springdoc.org</a>
 */
@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Dio Cloud Parking API")
                        .description("Spring parking sample application")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")))
                ;
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("parking")
                .packagesToScan("me.dio.parking.controller")
                .build();
    }

    @Bean
    public SecurityScheme securityScheme() {
        return new SecurityScheme()
                .name("parkingapi")
                .scheme("basic")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)

                ;
    }

}
