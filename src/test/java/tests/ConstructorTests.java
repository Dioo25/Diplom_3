package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;

public class ConstructorTests {

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverFactory.create();
        driver.get("https://stellarburgers.nomoreparties.site/");
    }

    @After
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void openConstructorTest() {

    }
}
