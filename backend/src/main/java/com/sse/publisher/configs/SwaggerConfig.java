package com.sse.publisher.configs;

import com.sse.publisher.constants.HttpConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Value("${swaggerBaseUrl}")
    private String baseUrl;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .host(baseUrl)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sse.publisher.controller"))
                .paths(PathSelectors.any()).build()
                .globalRequestParameters(Collections.singletonList(getRequestTraceId()));
    }

    private RequestParameter getRequestTraceId() {
        return new RequestParameterBuilder()
                .name(HttpConstants.TRACE_ID_HEADER)
                .description("Cross transaction unique ID")
                .in(ParameterType.HEADER).required(true)
                .build();
    }
}

