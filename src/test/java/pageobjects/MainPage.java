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

    // селектор кнопки "Войти в аккаунт" на главной (если другая — замени xpath)
    private final By loginButton = By.xpath("//button[contains(text(),'Войти в аккаунт') or contains(.,'Войти в аккаунт')]");

    // селектор Личного кабинета (в шапке)
    private final By personalAccountButton = By.xpath("//p[contains(text(),'Личный кабинет') or contains(.,'Личный Кабинет') or //a[contains(@href,'/profile')]]");

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
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    @Step("Клик по 'Личный кабинет' (шапка)")
    public void clickPersonalAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(personalAccountButton)).click();
    }
}