package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Используем xpath через текст меток — более устойчиво (как в RegistrationPage)
    private final By loginToAccountButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath("//p[text()='Личный Кабинет']");
    private final By emailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordField = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By loginButton = By.xpath("//button[text()='Войти']");
    private final By loginLinks = By.xpath("//a[text()='Войти']"); // может присутствовать в разных формах

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Step("Нажать кнопку 'Войти в аккаунт' на главной")
    public void clickLoginToAccountButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginToAccountButton)).click();
    }

    @Step("Нажать 'Личный кабинет' на главной")
    public void clickPersonalAccountButton() {
        wait.until(ExpectedConditions.elementToBeClickable(personalAccountButton)).click();
    }

    @Step("Ввести email: {email}")
    public void setEmail(String email) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        field.clear();
        field.sendKeys(email);
    }

    @Step("Ввести пароль")
    public void setPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        field.clear();
        field.sendKeys(password);
    }

    @Step("Нажать кнопку 'Войти' в форме")
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    @Step("Выполнить вход (email + пароль)")
    public void login(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLoginButton();
    }

    @Step("Кликнуть ссылку 'Войти' в текущем отображаемом контексте (регистрация/восстановление и т.д.)")
    public void clickLoginLinkInVisibleForm() {
        // Иногда на странице может быть несколько ссылок "Войти" (в форме регистрации и в форме восстановления).
        // Найдём все и кликнем первую видимую.
        List<WebElement> links = driver.findElements(loginLinks);
        for (WebElement link : links) {
            try {
                if (link.isDisplayed() && link.isEnabled()) {
                    link.click();
                    return;
                }
            } catch (StaleElementReferenceException ignored) {
            }
        }
        // Если не нашли — кидаем описательную ошибку
        throw new NoSuchElementException("Не найдена видимая ссылка 'Войти' для перехода на страницу логина");
    }

    @Step("Проверить, отображается ли личный кабинет (профиль)")
    public boolean isProfileVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(personalAccountButton)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}