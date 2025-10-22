package tests;

import api.UserApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import pageobjects.LoginPage;
import pageobjects.MainPage;
import utils.DriverFactory;

public class LoginTests {

    private WebDriver driver;
    private LoginPage loginPage;
    private MainPage mainPage;
    private String email;
    private String password;
    private String name;
    private String token;

    @Before
    public void setUp() {
        driver = DriverFactory.create();
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);

        email = UserApiClient.randomEmail();
        password = "password123";
        name = "AutoUser";

        UserApiClient.createUser(email, password, name);
        token = UserApiClient.loginUserAndGetToken(email, password);
        mainPage.open();
    }

    @After
    public void tearDown() {
        if (token != null) {
            UserApiClient.deleteUser(token);
        }
        driver.quit();
    }

    @Test
    @DisplayName("Логин через кнопку 'Войти в аккаунт'")
    @Description("Проверяем авторизацию через кнопку на главной странице")
    public void testLoginViaMainButton() {
        mainPage.clickLoginButton();
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickLogin();
        Assert.assertTrue("Не удалось войти через кнопку 'Войти в аккаунт'", loginPage.isLoginSuccessful());
    }

    @Test
    @DisplayName("Логин через кнопку 'Личный кабинет'")
    @Description("Проверяем вход через кнопку 'Личный кабинет'")
    public void testLoginViaPersonalAccount() {
        mainPage.clickPersonalAccount();
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickLogin();
        Assert.assertTrue("Не удалось войти через 'Личный кабинет'", loginPage.isLoginSuccessful());
    }

    @Test
    @DisplayName("Логин через форму регистрации")
    @Description("Проверяем вход через ссылку 'Войти' на странице регистрации")
    public void testLoginViaRegistrationForm() {
        driver.get("https://stellarburgers.education-services.ru/register");
        loginPage.clickRegistrationLoginButton();
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickLogin();
        Assert.assertTrue("Не удалось войти через форму регистрации", loginPage.isLoginSuccessful());
    }

    @Test
    @DisplayName("Логин через форму восстановления пароля")
    @Description("Проверяем вход через ссылку 'Войти' на странице восстановления пароля")
    public void testLoginViaPasswordRestore() {
        driver.get("https://stellarburgers.education-services.ru/forgot-password");
        loginPage.clickRestoreLoginButton();
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickLogin();
        Assert.assertTrue("Не удалось войти через форму восстановления пароля", loginPage.isLoginSuccessful());
    }
}