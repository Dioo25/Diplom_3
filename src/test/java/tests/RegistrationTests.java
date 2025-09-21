package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageobjects.MainPage;
import pageobjects.RegistrationPage;
import utils.DriverFactory;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class RegistrationTests {

    private WebDriver driver;
    private RegistrationPage registrationPage;
    private MainPage mainPage;

    @Before
    public void setUp() {
        driver = DriverFactory.create();
        driver.get("https://stellarburgers.nomoreparties.site/");
        mainPage = new MainPage(driver);
        mainPage.clickLoginAccountButton(); // переход к странице регистрации/логина
        registrationPage = new RegistrationPage(driver);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void registrationPositiveTest() {
        registrationPage.setName("Иван");
        registrationPage.setEmail("ivan@example.com");
        registrationPage.setPassword("123456");
        registrationPage.clickRegister();
        assertTrue("Регистрация должна пройти успешно", registrationPage.isRegistrationSuccessful());
    }

    @Test
    public void registrationWithShortPasswordTest() {
        registrationPage.setName("Пётр");
        registrationPage.setEmail("petr@example.com");
        registrationPage.setPassword("123"); // короткий пароль
        registrationPage.clickRegister();
        assertTrue("Должно отображаться сообщение об ошибке", registrationPage.isErrorMessageDisplayed());
    }
}