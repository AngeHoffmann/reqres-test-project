## Задание
Используя сервис с тестовым REST-API https://reqres.in/, написать 2 автотеста

### 1. GET https://reqres.in/api/users?page=2
- получить список пользователей на стр.2;
- проверить код ответа 200;
- создать класс User, на который будет мапиться ответ (использовать Lombok-аннотации)
- смапить тело ответа на List\<User\> (можно посредством RestAssured)
- проверить, что поля "email", "lastName" для всех User из списка - not null (AssertJ -> SoftAssertions -> assertSoftly)

### 2. POST https://reqres.in/api/users
- создать параметризованный тест;
- в качестве источника данных для теста использовать статический метод;
- в методе создавать и возвращать 2-х пользователей:
    - new User(1, "FirstName1", "LastName1", "user1@user.com", "https://reqres.in/img1.jpg")
    - new User(2, "FirstName2", "LastName2", "user2@user.com", "https://reqres.in/img2.jpg")
- для каждого отправить POST-запрос на /api/users;
- проверить код ответа 201;
- смапить тело ответа на объект User;
- проверить, что в ответе пришли те же значения полей, что были в запросе (поле "createdAt" игнорировать)

### Примечания
- pom.xml уже в готовом виде;
- большинство Spring-аннотаций также есть*;
- целевой url и его эндпоинт users указать в application.yaml;
- см. также другие примечания в коде;
- по результатам прогона АТ сформировать allure-отчет (mvn allure:serve)

[*] **в комментариях в коде написать, что делают аннотации:**
- @Configuration
- @Bean
- @PostConstruct
- @Service