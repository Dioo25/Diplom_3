package api;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.UUID;

public class UserApiClient {

    static {
        // Базовый URL задаём один раз для всего клиента
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

    @Step("Удалить пользователя по accessToken")
    public Response deleteUser(String accessToken) {
        if (accessToken == null || accessToken.isBlank()) {
            throw new IllegalArgumentException("accessToken required for deleteUser");
        }

        // Если токен пришёл без префикса "Bearer ", добавим.
        String headerValue = accessToken.startsWith("Bearer ") ? accessToken : "Bearer " + accessToken;

        return RestAssured.given()
                .header("Authorization", headerValue)
                .delete("/api/auth/user")
                .andReturn();
    }

    /**
     * Удобный метод: удаляет пользователя по объекту User.
     * Логинится, получает accessToken и вызывает удаление.
     */
    @Step("Удалить пользователя: {user.email}")
    public Response deleteUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null for deleteUser(User)");
        }

        // Попытка залогиниться, чтобы получить токен
        Response loginResponse = loginUser(user);

        // Ожидаем успешный логин (будет ресурс теста контролировать проверками статуса)
        if (loginResponse == null || loginResponse.getStatusCode() != 200) {
            // Если логин не удался — пытаемся извлечь токен всё равно (на случай нестандартного ответа),
            // но логируем/бросаем исключение, чтобы тесты не молча падали.
            throw new IllegalStateException("Не удалось получить accessToken: логин вернул код " +
                    (loginResponse == null ? "null" : loginResponse.getStatusCode()));
        }

        // Попробуем извлечь поле accessToken из тела ответа
        String rawToken = null;
        try {
            rawToken = loginResponse.jsonPath().getString("accessToken");
        } catch (Exception ignore) {
        }

        if (rawToken == null || rawToken.isBlank()) {
            throw new IllegalStateException("accessToken не найден в ответе при логине пользователя " + user.getEmail());
        }

        // Иногда API может вернуть accessToken со словом "Bearer " в начале — нормализуем.
        String tokenForHeader = rawToken.startsWith("Bearer ") ? rawToken : "Bearer " + rawToken;

        // Вызов реального удаления
        return RestAssured.given()
                .header("Authorization", tokenForHeader)
                .delete("/api/auth/user")
                .andReturn();
    }
}