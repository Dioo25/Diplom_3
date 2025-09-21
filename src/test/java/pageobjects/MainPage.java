package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final String baseUrl;
    private final By loginButton = By.xpath("//*[contains(text(),'Войти в аккаунт')]");
    private final By personalAccountLink = By.xpath("//a[contains(text(),'Личный кабинет')]");
    private final By bunsTab = By.xpath("//*[contains(text(),'Булки')]");
    private final By saucesTab = By.xpath("//*[contains(text(),'Соусы')]");
    private final By fillingsTab = By.xpath("//*[contains(text(),'Начинки')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        this.baseUrl = System.getProperty("baseUrl", "https://stellarburgers.nomoreparties.site");
    }

    @Step("Open main page")
    public void open() {
        driver.get(baseUrl);
    }

    @Step("Click 'Войти в аккаунт' on main")
    public void clickLoginAccountButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    @Step("Click 'Личный кабинет' link")
    public void clickPersonalAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(personalAccountLink)).click();
    }

    @Step("Click 'Булки' tab")
    public void clickBuns() {
        wait.until(ExpectedConditions.elementToBeClickable(bunsTab)).click();
    }

    @Step("Click 'Соусы' tab")
    public void clickSauces() {
        wait.until(ExpectedConditions.elementToBeClickable(saucesTab)).click();
    }

    @Step("Click 'Начинки' tab")
    public void clickFillings() {
        wait.until(ExpectedConditions.elementToBeClickable(fillingsTab)).click();
    }
}
