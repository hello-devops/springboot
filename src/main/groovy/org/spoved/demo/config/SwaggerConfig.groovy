package org.spoved.demo.config

import com.google.common.base.Predicate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.service.VendorExtension
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

import static com.google.common.base.Predicates.or
import static springfox.documentation.builders.PathSelectors.ant

@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage('org.spoved.demo.web'))
                .paths(paths())
                .build()
                .apiInfo(apiInfo())
    }

    static Predicate<String> paths() {
        return or(
                ant("/v1/**")
        )
    }

    static ApiInfo apiInfo() {
        new ApiInfo('DEMO API', 'DEMO API 문서입니다.', '1.0', '',
                new Contact('demo', 'https://spoved.org', 'hello@crazyguys.me'),
                '', '', new ArrayList<VendorExtension>())
    }
}
