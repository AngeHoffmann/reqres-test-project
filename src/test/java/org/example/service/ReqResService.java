package org.example.service;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@Service // Аннотация для классов сервисного уровня, которые содержат бизнес-логику.
public class ReqResService {
    @Value("${base.url}")
    private String baseUrl;

    @Value("${users.endpoint}")
    private String usersEndpoint;

    @Async
    @Step("Получение списка пользователей на странице")
    public List<User> getUsersFromPage(int pageNumber) {
        Response response = given()
                .queryParam("page", pageNumber)
                .get();

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(200);
        });

        List<User> users = response.jsonPath().getList("data", User.class);

        assertSoftly(softly-> {
            for (User user : users) {
                softly.assertThat(user.getEmail()).isNotNull();
                softly.assertThat(user.getLastName()).isNotNull();
            }
        });

        return users;
    }

    @Async
    @Step("Создание пользователя")
    public User createUser(User user) {
        Response response = given()
                .body(user)
                .contentType("application/json")
                .post();

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(201);
        });

        User createdUser = response.as(User.class);

        assertSoftly(softly -> {
            softly.assertThat(createdUser.getFirstName()).isEqualTo(user.getFirstName());
            softly.assertThat(createdUser.getLastName()).isEqualTo(user.getLastName());
            softly.assertThat(createdUser.getEmail()).isEqualTo(user.getEmail());
            softly.assertThat(createdUser.getAvatar()).isEqualTo(user.getAvatar());
        });

        return createdUser;


    }
}
