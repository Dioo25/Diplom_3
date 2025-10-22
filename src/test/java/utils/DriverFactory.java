package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Фабрика для запуска Chrome и Yandex браузеров.
 * Пример запуска:
 *   mvn test -Dbrowser=chrome
 *   mvn test -Dbrowser=yandex -Dyandex.binary="C:\\Path\\To\\Yandex\\browser.exe"
 */
public class DriverFactory {

    public static WebDriver create() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        if (browser.equals("yandex")) {
            return createYandex();
        }
        return createChrome();
    }

    private static WebDriver createChrome() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        List<String> args = new ArrayList<>();
        args.add("--no-sandbox");
        args.add("--disable-dev-shm-usage");
        args.add("--disable-infobars");
        args.add("--disable-extensions");
        args.add("--start-maximized");
        options.addArguments(args);
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        return driver;
    }

    private static WebDriver createYandex() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        String binary = System.getProperty("yandex.binary");
        if (binary == null || binary.isEmpty()) {
            binary = System.getProperty("user.home")
                    + "\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe";
        }
        options.setBinary(binary);
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--start-maximized");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        return driver;
    }
}