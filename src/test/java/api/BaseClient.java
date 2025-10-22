package api;

import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;

public class BaseClient {
    protected RequestSpecification getBaseSpec() {
        return RestAssured.given()
                .baseUri("https://stellarburgers.education-services.ru")
                .header("Content-Type", "application/json");
    }
}
