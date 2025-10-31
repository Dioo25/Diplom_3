package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import api.User;

import java.time.Duration;

public class RegistrationPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы
    private final By nameField = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By emailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordField = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By loginButtonOnLoginPage = By.xpath("//button[text()='Войти']");
    private final By errorText = By.xpath("//p[contains(@class, 'input__error')]");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Step("Вводим имя: {user.name}")
    public void setName(String name) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
        element.clear();
        element.sendKeys(name);
    }

    @Step("Вводим email: {user.email}")
    public void setEmail(String email) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        element.clear();
        element.sendKeys(email);
    }

    @Step("Вводим пароль")
    public void setPassword(String password) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        element.clear();
        element.sendKeys(password);
    }

    @Step("Нажимаем кнопку 'Зарегистрироваться'")
    public void clickRegisterButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(registerButton));
        button.click();
    }

    @Step("Регистрируем нового пользователя")
    public void register(User user) {
        setName(user.getName());
        setEmail(user.getEmail());
        setPassword(user.getPassword());
        clickRegisterButton();
    }

    @Step("Проверяем, открылась ли страница входа после регистрации")
    public boolean isLoginPageDisplayed() {
        try {
            wait.until(ExpectedConditions.urlContains("/login"));
            WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(loginButtonOnLoginPage));
            return loginButton.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Проверяем наличие ошибки под полем ввода")
    public String getErrorText() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorText)).getText();
        } catch (TimeoutException e) {
            return null;
        }
    }
}