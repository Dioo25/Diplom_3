package tests;

import api.User;
import api.UserApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import pageobjects.LoginPage;
import pageobjects.MainPage;
import pageobjects.RegistrationPage;
import pageobjects.PasswordRecoveryPage;
import utils.DriverFactory;

@DisplayName("Тесты логина (UI)")
public class LoginTests {

    private WebDriver driver;
    private LoginPage loginPage;
    private MainPage mainPage;
    private RegistrationPage registrationPage;
    private PasswordRecoveryPage recoveryPage;

    private final UserApiClient userApiClient = new UserApiClient();
    private User testUser;
    private String accessToken;

    @Before
    public void setUp() {
        // Создаём уникального пользователя через API
        String email = UserApiClient.randomEmail();
        testUser = new User(email, "Password123", "AutoTestUser");

        Response createResponse = userApiClient.createUser(testUser);
        Assert.assertNotNull("createResponse == null", createResponse);
        Assert.assertEquals("Не удалось создать пользователя через API", 200, createResponse.statusCode());

        // Сохраняем raw token (как вернул API)
        String rawToken = createResponse.jsonPath().getString("accessToken");
        accessToken = (rawToken != null && rawToken.startsWith("Bearer ")) ? rawToken : ("Bearer " + rawToken);

        // Запускаем браузер и открываем сайт
        driver = DriverFactory.getDriver(System.getProperty("browser", "chrome"));
        driver.get("https://stellarburgers.education-services.ru/");

        // PageObjects
        loginPage = new LoginPage(driver);
        mainPage = new MainPage(driver);
        registrationPage = new RegistrationPage(driver);
        recoveryPage = new PasswordRecoveryPage(driver);
    }

    @After
    public void tearDown() {
        // удаляем пользователя через API (по токену)
        try {
            if (accessToken != null && !accessToken.isBlank()) {
                Response del = userApiClient.deleteUserByToken(accessToken);
                if (del != null) {
                    System.out.println("Delete user status: " + del.statusCode());
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка при удалении пользователя в @After: " + e.getMessage());
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Логин — кнопка 'Войти в аккаунт' на главной")
    @Description("Проверяем, что пользователь может войти с помощью кнопки 'Войти в аккаунт' на главной странице")
    public void testLoginViaMainButton() {
        mainPage.clickLoginButton();
        loginPage.login(testUser.getEmail(), testUser.getPassword());
        Assert.assertTrue("Профиль не виден после логина через main button", loginPage.isProfileVisible());
    }

    @Test
    @DisplayName("Логин — 'Личный кабинет' (шапка)")
    @Description("Проверяем вход через кнопку 'Личный кабинет' в шапке")
    public void testLoginViaPersonalAccount() {
        mainPage.clickPersonalAccount();
        loginPage.login(testUser.getEmail(), testUser.getPassword());
        Assert.assertTrue("Профиль не виден после логина через personal account", loginPage.isProfileVisible());
    }

    @Test
    @DisplayName("Логин — через ссылку в форме регистрации")
    @Description("Открываем страницу регистрации, кликаем ссылку 'Войти' в форме регистрации и логинимся")
    public void testLoginViaRegistrationFormLink() {
        // Надёжно переходим на страницу регистрации по URL (если сайт поддерживает этот путь)
        driver.get("https://stellarburgers.education-services.ru/register");

        // В форме регистрации есть ссылка "Войти" — используем универсальный метод,
        // который найдёт видимую ссылку "Войти" и кликнет её
        loginPage.clickLoginLinkInVisibleForm();

        // После перехода на страницу логина делаем вход
        loginPage.login(testUser.getEmail(), testUser.getPassword());
        Assert.assertTrue("Профиль не виден после логина через ссылку в регистрации", loginPage.isProfileVisible());
    }

    @Test
    @DisplayName("Логин — через форму восстановления пароля")
    @Description("Проверяем вход через ссылку 'Войти' в форме восстановления пароля")
    public void testLoginViaPasswordRecoveryForm() {
        driver.get("https://stellarburgers.education-services.ru/forgot-password");
        recoveryPage.clickLoginLink();
        loginPage.login(testUser.getEmail(), testUser.getPassword());
        Assert.assertTrue("Профиль не виден после логина через восстановление пароля", loginPage.isProfileVisible());
    }
}