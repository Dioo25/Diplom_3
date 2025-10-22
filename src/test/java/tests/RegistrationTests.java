package tests;

import api.UserApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import pageobjects.MainPage;
import pageobjects.RegistrationPage;
import utils.DriverFactory;

public class RegistrationTests {

    private WebDriver driver;
    private RegistrationPage registrationPage;
    private MainPage mainPage;
    private String token; // для удаления юзера

    @Before
    public void setUp() {
        driver = DriverFactory.create();
        mainPage = new MainPage(driver);
        registrationPage = new RegistrationPage(driver);
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
    @DisplayName("Успешная регистрация пользователя")
    @Description("Проверяем, что пользователь успешно регистрируется с валидными данными")
    public void testSuccessfulRegistration() {
        String email = UserApiClient.randomEmail();
        String password = "password123";
        String name = "TestUser";

        driver.get("https://stellarburgers.education-services.ru/register");
        registrationPage.setName(name);
        registrationPage.setEmail(email);
        registrationPage.setPassword(password);
        registrationPage.clickRegister();

        token = UserApiClient.loginUserAndGetToken(email, password);
        Assert.assertNotNull("Пользователь не создался", token);
    }

    @Test
    @DisplayName("Ошибка при регистрации с коротким паролем")
    @Description("Проверяем, что появляется сообщение об ошибке при вводе пароля короче 6 символов")
    public void testInvalidPasswordRegistration() {
        driver.get("https://stellarburgers.education-services.ru/register");
        registrationPage.setName("Test");
        registrationPage.setEmail(UserApiClient.randomEmail());
        registrationPage.setPassword("123");
        registrationPage.clickRegister();

        Assert.assertTrue("Ошибка пароля не отображается", registrationPage.isPasswordErrorVisible());
    }
}