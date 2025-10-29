package api.user;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.UUID;

import static io.restassured.RestAssured.given;

public class UserApiClient {

    // Рабочий стенд (строго тот, который ты указала)
    private static final String BASE_URL = "https://stellarburgers.education-services.ru";

    // Константы, которые тесты часто используют (если хочешь — измени)
    public static final String TEST_EMAIL = "test_user_autotest@mail.test";
    public static final String TEST_PASSWORD = "123456";
    public static final String TEST_NAME = "AutoTestUser";

    static {
        RestAssured.baseURI = BASE_URL;
    }

    // Утилита для генерации случайного email
    public static String randomEmail() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8) + "@test.mail";
    }

    // Создать пользователя по email/password/name
    public Response createUser(String email, String password, String name) {
        UserPayload payload = new UserPayload(email, password, name);
        return given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post("/api/auth/register");
    }

    // Перегрузка — принять payload объект
    public Response createUser(UserPayload payload) {
        return given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post("/api/auth/register");
    }

    // Логин — вернуть Response
    public Response loginRaw(String email, String password) {
        LoginPayload payload = new LoginPayload(email, password);
        return given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post("/api/auth/login");
    }

    // Логин и получение токена (или null)
    public String loginAndGetToken(String email, String password) {
        Response r = loginRaw(email, password);
        if (r != null && r.statusCode() == 200) {
            return r.then().extract().path("accessToken");
        }
        return null;
    }

    // Удаление пользователя по токену (возвращает Response)
    public Response deleteUser(String token) {
        if (token == null) return null;
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .when()
                .delete("/api/auth/user");
    }

    // --- Вспомогательные POJO для сериализации
    public static class UserPayload {
        public String email;
        public String password;
        public String name;

        public UserPayload() {}

        public UserPayload(String email, String password, String name) {
            this.email = email;
            this.password = password;
            this.name = name;
        }
    }

    public static class LoginPayload {
        public String email;
        public String password;

        public LoginPayload() {}

        public LoginPayload(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }
}