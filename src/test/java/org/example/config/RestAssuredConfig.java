package org.example.config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static io.restassured.filter.log.LogDetail.ALL;

@Configuration // Указывает, что класс содержит конфигурации Spring и может создавать бины
@ComponentScan("org.example")
public class RestAssuredConfig {

    @Value("${base.url}")
    private String baseUrl;

    @Value("${users.endpoint}")
    private String usersEndpoint;

    @Bean // Это обычный объект, который управляется Spring и находится внутри его DI-контейнера.
    public AllureRestAssured allureRestAssured() {
        return new AllureRestAssured();
    }

    @Bean
    public RequestSpecification requestSpec() {
        return RestAssured.given()
                .baseUri(baseUrl)
                .basePath(usersEndpoint)
                .filter(allureRestAssured())
                .log().all();
    }

    @PostConstruct // Используется для обозначения метода, который должен быть выполнен сразу после создания объекта и завершения работы конструктора, но до того, как объект будет использован.
    private void postConstruct() {
        RestAssured.requestSpecification = requestSpec();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(ALL);
    }
}
