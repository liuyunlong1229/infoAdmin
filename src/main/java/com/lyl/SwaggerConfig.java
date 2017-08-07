package com.lyl;

import io.swagger.annotations.ApiOperation;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket buildDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf())
                .select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))//controller路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo buildApiInf(){
      return new ApiInfoBuilder()
                .title("系统后台 RESTful-APIs")
                .termsOfServiceUrl("liuyunlong1229@sina.com")
                .description("api文档,POST添加,GET获取,PUT修改,DELETE删除")
                .contact(new Contact("刘运龙", "", "liuyunlong1229@sina.com"))
                .version("1.0")
                .build();

    }
}