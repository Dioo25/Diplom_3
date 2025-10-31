package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ConstructorPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By bunsTab = By.xpath("//*[normalize-space(text())='Булки' or contains(text(),'Булки')]");
    private final By saucesTab = By.xpath("//*[normalize-space(text())='Соусы' or contains(text(),'Соусы')]");
    private final By fillingsTab = By.xpath("//*[normalize-space(text())='Начинки' or contains(text(),'Начинки')]");
    private final By bunsSection = By.xpath("//div[contains(@class,'buns') or contains(.,'Булки')]");
    private final By saucesSection = By.xpath("//div[contains(@class,'sauces') or contains(.,'Соусы')]");
    private final By fillingsSection = By.xpath("//div[contains(@class,'fillings') or contains(.,'Начинки')]");
    private final By modalOverlay = By.xpath("//*[contains(@class,'Modal') and contains(@class,'overlay') or contains(@class,'modal_overlay')]");
    private final Duration TIMEOUT = Duration.ofSeconds(12);

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    @Step("Открыть страницу конструктора")
    public void open() {
        driver.get("https://stellarburgers.education-services.ru/");
        waitUntilNoOverlay();
    }

    @Step("Перейти к Булки")
    public void goToBuns() {
        clickSafely(bunsTab);
    }

    @Step("Проверить отображение секции Булки")
    public boolean isBunsVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(bunsSection)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Перейти к Соусы")
    public void goToSauces() {
        clickSafely(saucesTab);
    }

    @Step("Проверить отображение секции Соусы")
    public boolean isSaucesVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(saucesSection)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Перейти к Начинки")
    public void goToFillings() {
        clickSafely(fillingsTab);
    }

    @Step("Проверить отображение секции Начинки")
    public boolean isFillingsVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(fillingsSection)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    private void clickSafely(By locator) {
        waitUntilNoOverlay();
        try {
            WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
            el.click();
        } catch (ElementClickInterceptedException ex) {
            try {
                WebElement el = driver.findElement(locator);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
            } catch (Exception ignored) {
            }
        }
    }

    private void waitUntilNoOverlay() {
        try {
            wait.withTimeout(Duration.ofSeconds(5))
                    .until(ExpectedConditions.invisibilityOfElementLocated(modalOverlay));
        } catch (Exception ignored) {
        } finally {
            wait.withTimeout(TIMEOUT);
        }
    }
}