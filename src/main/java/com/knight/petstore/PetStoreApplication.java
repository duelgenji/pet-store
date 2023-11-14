package com.knight.petstore;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.method.HandlerMethod;

@SpringBootApplication
public class PetStoreApplication {


//    @Bean
    public OperationCustomizer customize() {
        return new OperationCustomizer() {
            @Override
            public Operation customize(Operation operation, HandlerMethod handlerMethod) {

                ApiResponses apiResponses = new ApiResponses();
                ApiResponse apiResponse = new ApiResponse();
                operation.addParametersItem(new Parameter().in("header").required(true).description("Access token mandatory").name("X-Access-Token"));
                operation.addParametersItem(new Parameter().in("cookie").required(false).description("Caller indentifier").name("X-Caller-Name"));

                return operation;
            }
        };
    }


//    @Bean
//    public GroupedOpenApi publicApi() {
//        return GroupedOpenApi.builder()
//                .group("all")
//                .pathsToMatch("/**")
//                .addOperationCustomizer(customize())
//                .build();
//    }



    public static void main(String[] args) {
        SpringApplication.run(PetStoreApplication.class, args);
    }

}
