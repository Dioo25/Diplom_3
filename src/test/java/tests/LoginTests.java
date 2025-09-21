package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.LoginPage;
import utils.DriverFactory;

public class LoginTests {

    private WebDriver driver;
    private LoginPage loginPage;

    @Before
    public void setUp() {
        driver = DriverFactory.create();
        driver.get("https://stellarburgers.nomoreparties.site/");
        loginPage = new LoginPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void loginViaAccountButtonTest() {
        loginPage.clickLoginAccountButton();
        loginPage.setEmail("test@mail.com");
        loginPage.setPassword("password123");
        loginPage.clickLogin();
        assert loginPage.isLoginSuccessful();
    }

    @Test
    public void loginViaResetPasswordTest() {
        loginPage.clickResetPassword();
        loginPage.setEmail("test@mail.com");
        loginPage.setPassword("password123");
        loginPage.clickLogin();
        assert loginPage.isLoginSuccessful();
    }
}
