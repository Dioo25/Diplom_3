package tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import pageobjects.MainPage;
import utils.DriverFactory;

public class BaseTest {

    protected WebDriver driver;
    protected MainPage mainPage;

    @Before
    public void setUpBase() {
        // создаём драйвер (по умолчанию chrome). Можно указать -Dbrowser=yandex
        driver = DriverFactory.create();

        // базовая страница — MainPage использует свойство baseUrl (см. MainPage)
        mainPage = new MainPage(driver);

        // Если нужно принудительно переопределить базовый URL — передавайте -DbaseUrl=...
        // mainPage.open() вызывайте в конкретных тестах (они уже делают open() в своих @Before)
    }

    @After
    public void tearDownBase() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) { }
        }
    }
}