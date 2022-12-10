package com.wordNote.wordNote.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import javax.print.Doc

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun restAPI() :Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.wordNote.wordNote"))
            .paths(PathSelectors.any())
            .build()
    }

    private fun apiInfo() : ApiInfo {
        return ApiInfoBuilder()
            .title("Word Note API")
            .version("1.0.0")
            .description("나만의 단어장을 만들자")
            .build()
    }
}