package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private final By emailField = By.xpath("//input[@name='name' or @name='email']");
    private final By passwordField = By.xpath("//input[@name='Пароль' or @type='password']");
    private final By loginButton = By.xpath("//button[text()='Войти']");
    private final By profileButton = By.xpath("//p[text()='Личный Кабинет']");
    private final By registrationLoginButton = By.xpath("//a[text()='Войти']");
    private final By restoreLoginButton = By.xpath("//a[text()='Войти']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void setEmail(String email) {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(emailField));
        field.clear();
        field.sendKeys(email);
    }

    public void setPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        field.clear();
        field.sendKeys(password);
    }

    public void clickLogin() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        scrollIntoView(button);
        button.click();
    }

    public void clickRegistrationLoginButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(registrationLoginButton));
        scrollIntoView(button);
        button.click();
    }

    public void clickRestoreLoginButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(restoreLoginButton));
        scrollIntoView(button);
        button.click();
    }

    public boolean isLoginSuccessful() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(profileButton));
            return driver.findElement(profileButton).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    private void scrollIntoView(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", el);
    }
}