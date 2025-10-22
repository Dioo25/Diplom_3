package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath("//p[text()='Личный Кабинет']");
    private final By bunsSection = By.xpath("//span[text()='Булки']");
    private final By saucesSection = By.xpath("//span[text()='Соусы']");
    private final By fillingsSection = By.xpath("//span[text()='Начинки']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("https://stellarburgers.education-services.ru/");
    }

    public void clickLoginButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        scrollIntoView(button);
        button.click();
    }

    public void clickPersonalAccount() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(personalAccountButton));
        scrollIntoView(button);
        button.click();
    }

    public void clickBuns() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(bunsSection));
        scrollIntoView(el);
        el.click();
    }

    public void clickSauces() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(saucesSection));
        scrollIntoView(el);
        el.click();
    }

    public void clickFillings() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(fillingsSection));
        scrollIntoView(el);
        el.click();
    }

    private void scrollIntoView(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", el);
    }
}