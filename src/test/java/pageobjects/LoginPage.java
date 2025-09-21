package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By emailInput = By.xpath("//input[@name='email' or @name='name' or contains(@placeholder,'Email')]");
    private final By passwordInput = By.xpath("//input[@name='password' or contains(@placeholder,'Пароль')]");
    private final By loginButton = By.xpath("//button[contains(.,'Войти')]");
    private final By registerLink = By.xpath("//a[contains(.,'Зарегистрироваться')]");
    private final By forgotPasswordLink = By.xpath("//a[contains(.,'Восстановить') or contains(.,'Забыли')]");

    public LoginPage(org.openqa.selenium.WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    @Step("Set email")
    public void setEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).clear();
        driver.findElement(emailInput).sendKeys(email);
    }

    @Step("Set password")
    public void setPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    @Step("Click login")
    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    @Step("Click register link")
    public void clickRegisterLink() {
        wait.until(ExpectedConditions.elementToBeClickable(registerLink)).click();
    }

    @Step("Click reset password link")
    public void clickResetPassword() {
        wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink)).click();
    }

    @Step("Return true if login appears successful")
    public boolean isLoginSuccessful() {
        try {
            AccountPage ap = new AccountPage(driver);
            return ap.isOnAccountPage();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Check login form displayed")
    public boolean isLoginFormDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
