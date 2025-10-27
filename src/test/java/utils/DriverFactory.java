package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    /**
     * Выбор браузера через system property:
     * -Dbrowser=chrome  (по умолчанию)
     * -Dbrowser=yandex  (подразумевается, что путь к бинарнику YANDEX_BIN задан)
     */
    public static WebDriver create() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        if ("yandex".equals(browser)) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            String yandexBinary = System.getenv("YANDEX_BIN");
            if (yandexBinary != null && !yandexBinary.isEmpty()) {
                options.setBinary(yandexBinary);
            } else {
                // Попробовать стандартный путь для Yandex на Windows (если установлен)
                options.setBinary("C:\\Users\\%USERNAME%\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
            }
            return new ChromeDriver(options);
        } else {
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver();
        }
    }
}