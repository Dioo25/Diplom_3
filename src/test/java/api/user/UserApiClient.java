package api;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.UUID;

public class UserApiClient {

    static {
        RestAssured.baseURI = "https://stellarburgers.education-services.ru";
    }

    @Step("Генерация рандомного email")
    public static String randomEmail() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8) + "@test.ru";
    }

    @Step("Создать пользователя: {user.email}")
    public Response createUser(User user) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(user)
                .post("/api/auth/register")
                .andReturn();
    }

    @Step("Вход пользователя: {user.email}")
    public Response loginUser(User user) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(user)
                .post("/api/auth/login")
                .andReturn();
    }

    /**
     * Удаление по access token (взять токен — и передать в "Bearer ...").
     * Переименовано, чтобы избежать неоднозначности с deleteUser(User).
     */
    @Step("Удалить пользователя по токену")
    public Response deleteUserByToken(String accessToken) {
        if (accessToken == null || accessToken.isBlank()) {
            throw new IllegalArgumentException("accessToken required for deleteUserByToken");
        }
        String headerValue = accessToken.startsWith("Bearer ") ? accessToken : "Bearer " + accessToken;
        return RestAssured.given()
                .header("Authorization", headerValue)
                .delete("/api/auth/user")
                .andReturn();
    }

    /**
     * Удалить пользователя через логин (удобный метод).
     * Логинится, берёт токен, вызывает удаление.
     */
    @Step("Удалить пользователя: {user.email}")
    public Response deleteUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null for deleteUser(User)");
        }
        Response loginResponse = loginUser(user);
        if (loginResponse == null || loginResponse.statusCode() != 200) {
            throw new IllegalStateException("Не удалось получить accessToken: логин вернул код " +
                    (loginResponse == null ? "null" : loginResponse.statusCode()));
        }
        String rawToken = loginResponse.jsonPath().getString("accessToken");
        if (rawToken == null || rawToken.isBlank()) {
            throw new IllegalStateException("accessToken не найден в ответе при логине пользователя " + user.getEmail());
        }
        String tokenForHeader = rawToken.startsWith("Bearer ") ? rawToken : "Bearer " + rawToken;
        return RestAssured.given()
                .header("Authorization", tokenForHeader)
                .delete("/api/auth/user")
                .andReturn();
    }
}