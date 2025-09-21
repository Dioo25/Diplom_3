package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AccountPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By accountHeader = By.xpath("//*[contains(text(),'Личный кабинет') or contains(.,'Профиль')]");

    public AccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    @Step("Check account page visible")
    public boolean isOnAccountPage() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(accountHeader)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
