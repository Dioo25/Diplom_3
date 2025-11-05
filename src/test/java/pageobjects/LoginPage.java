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

    // селекторы
    private final By loginToAccountButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath("//p[contains(text(),'Личный кабинет') or contains(.,'Личный Кабинет') or //a[contains(@href,'/profile')]]");
    private final By emailField = By.xpath("//label[text()='Email']/following-sibling::input | //input[@name='email' or @type='email']");
    private final By passwordField = By.xpath("//label[text()='Пароль']/following-sibling::input | //input[@name='password']");
    private final By loginButton = By.xpath("//button[text()='Войти']");
    private final By loginLinks = By.xpath("//a[text()='Войти']");
    private final By registerLinks = By.xpath("//a[contains(text(),'Зарегистрироваться') or contains(text(),'Регистрация')]");
    private final By registrationNameField = By.xpath("//label[text()='Имя']/following-sibling::input | //input[@name='name']");

    // overlay (в логах проекта встречается класс Modal_modal_overlay__x2ZCr)
    private final By modalOverlay = By.className("Modal_modal_overlay__x2ZCr");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Step("Нажать кнопку 'Войти в аккаунт' на главной")
    public void clickLoginToAccountButton() {
        safeClick(loginToAccountButton);
        // ждём появления формы (email или регистрация)
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.or(
                            ExpectedConditions.visibilityOfElementLocated(emailField),
                            ExpectedConditions.visibilityOfElementLocated(registrationNameField)
                    ));
        } catch (TimeoutException ignored) { }
    }

    @Step("Нажать 'Личный кабинет' на главной")
    public void clickPersonalAccountButton() {
        safeClick(personalAccountButton);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(8))
                    .until(ExpectedConditions.or(
                            ExpectedConditions.visibilityOfElementLocated(emailField),
                            ExpectedConditions.urlContains("/profile")
                    ));
        } catch (TimeoutException ignored) { }
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
        safeClick(loginButton);
    }

    @Step("Выполнить вход (email + пароль)")
    public void login(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLoginButton();
    }

    @Step("Кликнуть ссылку 'Войти' в текущем отображаемом контексте (регистрация/восстановление и т.д.)")
    public void clickLoginLinkInVisibleForm() {
        List<WebElement> links = driver.findElements(loginLinks);
        for (WebElement link : links) {
            try {
                if (link.isDisplayed() && link.isEnabled()) {
                    safeClick(link);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
                    return;
                }
            } catch (StaleElementReferenceException ignored) { }
        }
        throw new NoSuchElementException("Не найдена видимая ссылка 'Войти' для перехода на страницу логина");
    }

    @Step("Кликнуть ссылку 'Зарегистрироваться' в текущем отображаемом контексте")
    public void clickRegisterLinkInVisibleForm() {
        List<WebElement> links = driver.findElements(registerLinks);
        for (WebElement link : links) {
            try {
                if (link.isDisplayed() && link.isEnabled()) {
                    safeClick(link);
                    new WebDriverWait(driver, Duration.ofSeconds(12))
                            .until(ExpectedConditions.visibilityOfElementLocated(registrationNameField));
                    return;
                }
            } catch (StaleElementReferenceException ignored) { }
        }
        throw new NoSuchElementException("Не найдена видимая ссылка 'Зарегистрироваться' в текущем контексте");
    }

    @Step("Проверить, отображается ли личный кабинет (профиль)")
    public boolean isProfileVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(personalAccountButton)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // -------------------- HELPERS --------------------

    /**
     * Универсальный safeClick: принимает By.
     * Ждёт исчезновения overlay, ждёт кликабельность, пробует кликнуть обычным способом,
     * при перехвате клика — выполняет клик через JS.
     */
    private void safeClick(By by) {
        waitForOverlayToDisappearSafely();
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(by));
        try {
            el.click();
        } catch (ElementClickInterceptedException | StaleElementReferenceException ex) {
            jsClick(el);
        }
    }

    /**
     * overload: безопасный клик по уже найденному WebElement
     */
    private void safeClick(WebElement element) {
        waitForOverlayToDisappearSafely();
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (ElementClickInterceptedException | StaleElementReferenceException ex) {
            jsClick(element);
        }
    }

    private void waitForOverlayToDisappearSafely() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(6));
            shortWait.until(ExpectedConditions.invisibilityOfElementLocated(modalOverlay));
        } catch (Exception ignored) { }
    }

    private void jsClick(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            // если и JS не сработал —  ещё раз обычный клик (падать не будем, тесты увидят проблему)
            try {
                element.click();
            } catch (Exception ignored) { }
        }
    }
}