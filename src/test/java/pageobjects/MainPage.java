package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By loginButton = By.xpath("//button[contains(text(),'Войти в аккаунт') or contains(.,'Войти в аккаунт')]");
    private final By personalAccountButton = By.xpath("//p[contains(text(),'Личный кабинет') or contains(.,'Личный Кабинет') or //a[contains(@href,'/profile')]]");
    private final By modalOverlay = By.className("Modal_modal_overlay__x2ZCr");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    @Step("Открыть главную")
    public void open() {
        driver.get("https://stellarburgers.education-services.ru/");
    }

    @Step("Клик по кнопке 'Войти в аккаунт' на главной")
    public void clickLoginButton() {
        safeClick(loginButton);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.or(
                            ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Email']/following-sibling::input")),
                            ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Имя']/following-sibling::input"))
                    ));
        } catch (TimeoutException ignored) {}
    }

    @Step("Клик по 'Личный кабинет' (шапка)")
    public void clickPersonalAccount() {
        safeClick(personalAccountButton);
    }

    // -------------------- HELPERS --------------------

    private void safeClick(By by) {
        waitForOverlayToDisappearSafely();
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(by));
        try {
            el.click();
        } catch (ElementClickInterceptedException | StaleElementReferenceException ex) {
            jsClick(el);
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
            try { element.click(); } catch (Exception ignored) { }
        }
    }
}