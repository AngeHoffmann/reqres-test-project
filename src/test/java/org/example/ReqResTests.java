package org.example;

import org.example.config.AtConfig;
import org.example.entity.User;
import org.example.service.ReqResService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest(classes = AtConfig.class)
public class ReqResTests {

    @Autowired
    private ReqResService reqResService;

    @Test
    @DisplayName("Получение страницы 2 со списком пользователей")
    void getUsersPage() {
        reqResService.getUsersFromPage(2);
    }

    @ParameterizedTest(name = "Создание пользователя - {index}")
    @MethodSource("userProvider")
    void createUser(User user) {
        reqResService.createUser(user);
    }

    static Stream<User> userProvider() {
        return Stream.of(
                new User(1, "FirstName1", "LastName1", "user1@user.com", "https://reqres.in/img1.jpg"),
                new User(2, "FirstName2", "LastName2", "user2@user.com", "https://reqres.in/img2.jpg")
        );
    }
}
