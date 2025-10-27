package tests;

import api.UserApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.RegistrationPage;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

@DisplayName("Тесты регистрации")
public class RegistrationTests {

    private WebDriver driver;
    private RegistrationPage registrationPage;

    @Before
    public void setUp() {
        driver = DriverFactory.create();
        registrationPage = new RegistrationPage(driver);
        driver.get("https://stellarburgers.education-services.ru/register");
    }

    @After
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    @DisplayName("Успешная регистрация")
    @Description("Регистрация с валидными данными")
    public void testSuccessfulRegistration() {
        String email = UserApiClient.randomEmail();
        registrationPage.register("TestUser", email, "123456");
        assertTrue(registrationPage.isRegistrationSuccess());
    }

    @Test
    @DisplayName("Регистрация с коротким паролем")
    @Description("Попытка регистрации с паролем меньше 6 символов")
    public void testShortPasswordRegistration() {
        String email = UserApiClient.randomEmail();
        registrationPage.register("TestUser", email, "123");
        assertEquals("Некорректный пароль", registrationPage.getErrorMessage());
    }
}