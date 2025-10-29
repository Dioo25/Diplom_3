package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailField = By.name("email");
    private final By passwordField = By.name("password");
    private final By loginButton = By.xpath("//button[text()='Войти']");
    private final By profileButton = By.xpath("//p[contains(text(),'Личный кабинет')]");
    private final By registrationLoginButton = By.xpath("//a[text()='Войти']");
    private final By restoreLoginButton = By.xpath("//a[text()='Войти']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Ввести email: {email}")
    public void setEmail(String email) {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(emailField));
        field.clear();
        field.sendKeys(email);
    }

    @Step("Ввести пароль")
    public void setPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        field.clear();
        field.sendKeys(password);
    }

    @Step("Нажать кнопку 'Войти'")
    public void clickLogin() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        btn.click();
    }

    @Step("Выполнить логин пользователя {email}")
    public void login(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLogin();
    }

    @Step("Проверить, что пользователь вошёл в систему")
    public boolean isLoggedIn() {
        return driver.findElements(profileButton).size() > 0;
    }
}