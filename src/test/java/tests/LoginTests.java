package tests;

import api.UserApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.LoginPage;

import static org.junit.Assert.assertTrue;

@DisplayName("Тесты входа")
public class LoginTests {

    private WebDriver driver;
    private LoginPage loginPage;

    @Before
    public void setUp() {
        driver = DriverFactory.create();
        loginPage = new LoginPage(driver);
        driver.get("https://stellarburgers.education-services.ru/login");
    }

    @After
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    @DisplayName("Вход через кнопку 'Войти в аккаунт'")
    @Description("Проверка входа на главной странице")
    public void testLoginMainButton() {
        loginPage.login(UserApiClient.TEST_EMAIL, UserApiClient.TEST_PASSWORD);
        assertTrue(loginPage.isLoggedIn());
    }

    @Test
    @DisplayName("Вход через Личный кабинет")
    @Description("Проверка входа через кнопку Личный кабинет")
    public void testLoginPersonalAccount() {
        loginPage.loginViaPersonalAccount(UserApiClient.TEST_EMAIL, UserApiClient.TEST_PASSWORD);
        assertTrue(loginPage.isLoggedIn());
    }
}