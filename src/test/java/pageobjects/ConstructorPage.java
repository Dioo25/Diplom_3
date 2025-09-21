package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ConstructorPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By bunsTab = By.xpath("//*[contains(text(),'Булки')]");
    private final By saucesTab = By.xpath("//*[contains(text(),'Соусы')]");
    private final By fillingsTab = By.xpath("//*[contains(text(),'Начинки')]");
    private final By activeTab = By.xpath("//*[contains(@class,'tab') and contains(@class,'active')] | //*[contains(@class,'tab-tab_type_current')]");

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    @Step("Click buns")
    public void clickBuns() {
        wait.until(ExpectedConditions.elementToBeClickable(bunsTab)).click();
    }

    @Step("Click sauces")
    public void clickSauces() {
        wait.until(ExpectedConditions.elementToBeClickable(saucesTab)).click();
    }

    @Step("Click fillings")
    public void clickFillings() {
        wait.until(ExpectedConditions.elementToBeClickable(fillingsTab)).click();
    }

    @Step("Get active tab text")
    public String getActiveTabText() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(activeTab)).getText();
        } catch (Exception e) {
            return "";
        }
    }
}
