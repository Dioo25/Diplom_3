package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    /**
     * Совместимый метод create() — многие тесты ожидают именно его.
     */
    public static WebDriver create() {
        String browser = System.getProperty("browser", "chrome");
        return getDriver(browser);
    }

    /**
     * Возвращает WebDriver по умолчанию. Удобно вызывать getDriver() напрямую.
     */
    public static WebDriver getDriver() {
        String browser = System.getProperty("browser", "chrome");
        return getDriver(browser);
    }

    /**
     * Возвращает WebDriver для заданного браузера.
     * Поддерживаем 'chrome' и 'yandex' (Yandex — ChromeDriver с бинарником Yandex).
     */
    public static WebDriver getDriver(String browser) {
        String b = (browser == null) ? "chrome" : browser.toLowerCase();

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*"); // для новых сборок Chrome

        if ("yandex".equals(b)) {
            String yandexBinary = System.getenv("YANDEX_BIN");
            if (yandexBinary != null && !yandexBinary.isEmpty()) {
                options.setBinary(yandexBinary);
            } else {
                // запасной путь — можно изменить под систему
                String user = System.getenv("USERNAME");
                options.setBinary("C:\\Users\\" + user + "\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
            }
        }

        return new ChromeDriver(options);
    }
}