package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import pageobjects.LoginPage;
import utils.DriverFactory;

@DisplayName("Тесты логина")
public class LoginTests {

    private WebDriver driver;
    private LoginPage loginPage;

    @Before
    public void setUp() {
        driver = DriverFactory.getDriver(System.getProperty("browser", "chrome"));
        driver.get("https://stellarburgers.education-services.ru/");
        loginPage = new LoginPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Логин через кнопку 'Войти в аккаунт'")
    @Description("Проверяем успешный вход через кнопку 'Войти в аккаунт'")
    public void testLoginMainButton() {
        loginPage.clickLoginToAccountButton();
        loginPage.login("test@example.com", "123456");
        Assert.assertTrue("Профиль не отобразился", loginPage.isProfileVisible());
    }

    @Test
    @DisplayName("Логин через 'Личный кабинет'")
    @Description("Проверяем успешный вход через кнопку 'Личный кабинет'")
    public void testLoginPersonalAccount() {
        loginPage.clickPersonalAccountButton();
        loginPage.login("test@example.com", "123456");
        Assert.assertTrue("Профиль не отобразился", loginPage.isProfileVisible());
    }
}