package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.ConstructorPage;

import static org.junit.Assert.assertTrue;

@DisplayName("Тесты конструктора")
public class ConstructorTests {

    private WebDriver driver;
    private ConstructorPage constructorPage;

    @Before
    public void setUp() {
        driver = utils.DriverFactory.create();
        constructorPage = new ConstructorPage(driver);
        driver.get("https://stellarburgers.education-services.ru/");
    }

    @After
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    @DisplayName("Переход к разделу Булки")
    @Description("Проверка, что раздел Булки открывается корректно")
    public void testBunsSection() {
        constructorPage.goToBuns();
        assertTrue(constructorPage.isBunsVisible());
    }

    @Test
    @DisplayName("Переход к разделу Соусы")
    @Description("Проверка, что раздел Соусы открывается корректно")
    public void testSaucesSection() {
        constructorPage.goToSauces();
        assertTrue(constructorPage.isSaucesVisible());
    }

    @Test
    @DisplayName("Переход к разделу Начинки")
    @Description("Проверка, что раздел Начинки открывается корректно")
    public void testFillingsSection() {
        constructorPage.goToFillings();
        assertTrue(constructorPage.isFillingsVisible());
    }
}