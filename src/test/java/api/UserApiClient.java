package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * API-клиент для работы с пользователями.
 * Использует RequestSpecification из BaseClient (getSpec()).
 */
public class UserApiClient extends BaseClient {

    @Step("Создать пользователя: {user.email}")
    public Response createUser(api.User user) {
        return given()
                .spec(getSpec())
                .body(user) // сериализация Jackson / Rest Assured
                .when()
                .post("/auth/register");
    }

    @Step("Логин пользователя: {email}")
    public Response loginUser(String email, String password) {
        var body = new LoginRequest(email, password);
        return given()
                .spec(getSpec())
                .body(body)
                .when()
                .post("/auth/login");
    }

    @Step("Логин пользователя (используя модель User): {user.email}")
    public Response loginUser(api.User user) {
        var body = new LoginRequest(user.getEmail(), user.getPassword());
        return given()
                .spec(getSpec())
                .body(body)
                .when()
                .post("/auth/login");
    }

    @Step("Удалить пользователя")
    public Response deleteUser(String token) {
        if (token == null) return null;
        return given()
                .spec(getSpec())
                .header("Authorization", token)
                .when()
                .delete("/auth/user");
    }

    // Вспомогательный DTO для логина
    private static class LoginRequest {
        public String email;
        public String password;
        public LoginRequest(String email, String password) { this.email = email; this.password = password; }
    }
}