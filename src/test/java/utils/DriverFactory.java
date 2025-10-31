package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverFactory {

    /**
     * Создание драйвера. По умолчанию — Chrome.
     * Если задан system property "browser=yandex", запускается Яндекс.Браузер.
     */
    public static WebDriver create() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");

        if ("yandex".equals(browser)) {
            // Указываем путь к бинарнику Яндекс.Браузера
            String yandexBinary = System.getenv("YANDEX_BIN");
            if (yandexBinary != null && !yandexBinary.isEmpty()) {
                options.setBinary(yandexBinary);
            } else {
                options.setBinary("C:\\Users\\" + System.getenv("USERNAME")
                        + "\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
            }
        }

        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        return driver;
    }

    /**
     * Создание драйвера для указанного браузера.
     * Временное изменение system property "browser".
     */
    public static WebDriver getDriver(String browser) {
        if (browser == null || browser.isBlank()) {
            return create();
        }
        String prev = System.getProperty("browser");
        try {
            System.setProperty("browser", browser.toLowerCase());
            return create();
        } finally {
            if (prev != null) {
                System.setProperty("browser", prev);
            } else {
                System.clearProperty("browser");
            }
        }
    }
}