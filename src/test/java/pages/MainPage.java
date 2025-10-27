package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {

    private final WebDriver driver;

    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт' or contains(.,'Войти в аккаунт')]");
    private final By personalAccount = By.xpath("//a[contains(@href,'account') or contains(text(),'Личный кабинет')]");
    private final By registerFormLoginLink = By.xpath("//a[contains(text(),'Войти')]");
    private final By restoreFormLoginLink = By.xpath("//a[contains(text(),'Войти')]");
    private final By bunsTab = By.xpath("//div[contains(text(),'Булки')]");
    private final By saucesTab = By.xpath("//div[contains(text(),'Соусы')]");
    private final By fillingsTab = By.xpath("//div[contains(text(),'Начинки')]");
    private final By activeTab = By.xpath("//div[contains(@class,'tab_tab_type_current') or contains(@class,'tab_tab_type_current__')]|//div[contains(@class,'tab_tab_type_current')]/..");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открыть главную")
    public void open() {
        driver.get("https://stellarburgers.education-services.ru");
    }

    @Step("Кликнуть 'Войти в аккаунт' на главной")
    public void clickLoginFromMain() {
        driver.findElement(loginButton).click();
    }

    @Step("Кликнуть 'Личный кабинет'")
    public void clickPersonalAccount() {
        driver.findElement(personalAccount).click();
    }

    @Step("Кликнуть вкладку Булки")
    public void clickBuns() {
        driver.findElement(bunsTab).click();
    }

    @Step("Кликнуть вкладку Соусы")
    public void clickSauces() {
        driver.findElement(saucesTab).click();
    }

    @Step("Кликнуть вкладку Начинки")
    public void clickFillings() {
        driver.findElement(fillingsTab).click();
    }

    @Step("Получить активную вкладку")
    public String getActiveTabText() {
        try {
            return driver.findElement(activeTab).getText();
        } catch (Exception e) {
            return "";
        }
    }
}