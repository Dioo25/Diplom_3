package tests;

import io.qameta.allure.junit4.AllureJunit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import ui.DriverFactory;

public abstract class BaseUiTest {

    protected WebDriver driver;

    @Rule
    public AllureJunit4 allure = new AllureJunit4();

    @Before
    public void setUp() {
        driver = DriverFactory.create();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}