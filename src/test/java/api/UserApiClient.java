package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.UUID;

public class UserApiClient {

    private static final String BASE_URL = "https://stellarburgers.education-services.ru";

    public static String randomEmail() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8) + "@test.ru";
    }

    public static Response createUser(String email, String password, String name) {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body("{\"email\":\"" + email + "\",\"password\":\"" + password + "\",\"name\":\"" + name + "\"}")
                .post(BASE_URL + "/api/auth/register");
    }

    public static String loginUserAndGetToken(String email, String password) {
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}")
                .post(BASE_URL + "/api/auth/login");
        return response.jsonPath().getString("accessToken");
    }

    public static void deleteUser(String token) {
        if (token != null && !token.isEmpty()) {
            RestAssured.given()
                    .header("Authorization", token)
                    .delete(BASE_URL + "/api/auth/user");
        }
    }
}