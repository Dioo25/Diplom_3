package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import pageobjects.RegistrationPage;
import api.UserApiClient;
import api.User;
import utils.DriverFactory;
import io.qameta.allure.Allure;

@DisplayName("Тесты регистрации")
public class RegistrationTests {

    private WebDriver driver;
    private RegistrationPage registrationPage;
    private UserApiClient userApiClient;
    private User testUser;

    @Before
    public void setUp() {
        Allure.step("Открываем страницу регистрации Stellar Burgers");

        driver = DriverFactory.getDriver(System.getProperty("browser", "chrome"));
        driver.get("https://stellarburgers.education-services.ru/register");

        registrationPage = new RegistrationPage(driver);
        userApiClient = new UserApiClient();
    }

    @After
    public void tearDown() {
        if (testUser != null) {
            try {
                userApiClient.deleteUser(testUser);
                Allure.step("Тестовый пользователь успешно удалён");
            } catch (Exception e) {
                Allure.step("⚠ Ошибка при удалении пользователя: " + e.getMessage());
            }
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Успешная регистрация нового пользователя")
    @Description("Проверяем регистрацию нового пользователя с валидными данными")
    public void testSuccessfulRegistration() {
        Allure.step("Создаём нового пользователя с валидными данными");

        testUser = new User(
                "test" + System.currentTimeMillis() + "@mail.ru",
                "123456",
                "Тест"
        );

        Allure.step("Выполняем регистрацию через UI");
        registrationPage.register(testUser);

        Allure.step("Проверяем, что после регистрации открылась страница входа");
        Assert.assertTrue(
                "Ожидалась страница входа после регистрации",
                registrationPage.isLoginPageDisplayed()
        );
    }
}