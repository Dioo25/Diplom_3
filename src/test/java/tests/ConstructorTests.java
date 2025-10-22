package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import pageobjects.MainPage;
import utils.DriverFactory;

public class ConstructorTests {

    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {
        driver = DriverFactory.create();
        mainPage = new MainPage(driver);
        mainPage.open();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Переход в раздел 'Булки'")
    @Description("Проверяем, что при клике на раздел 'Булки' открывается соответствующий контент")
    public void testBunsSection() {
        mainPage.clickSauces(); // сначала переключаемся на другой раздел
        mainPage.clickBuns();   // затем кликаем по Булкам

    }
}