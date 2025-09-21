package pageobjects;

import org.openqa.selenium.*;
import java.util.List;

public class RegistrationPage {
    private final WebDriver driver;
    private final long DEFAULT_TIMEOUT_MS = 12_000L;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    // --- Методы для тестов ---
    public void setName(String name) {
        removeOverlaysIfAny();
        WebElement el = findVisibleElement(DEFAULT_TIMEOUT_MS,
                By.xpath("//form//fieldset[1]//input"),
                By.xpath("//form//input[not(@type='password') and not(@type='email')][1]"),
                By.xpath("//input[@name='name']")
        );
        el.clear();
        el.sendKeys(name);
    }

    public void setEmail(String email) {
        removeOverlaysIfAny();
        WebElement el = findVisibleElement(DEFAULT_TIMEOUT_MS,
                By.xpath("//input[@name='email']"),
                By.cssSelector("input[type='email']"),
                By.cssSelector("input[placeholder*='Email']"),
                By.xpath("//form//input[not(@type='hidden')][2]")
        );
        el.clear();
        el.sendKeys(email);
    }

    public void setPassword(String password) {
        removeOverlaysIfAny();
        WebElement el = findVisibleElement(DEFAULT_TIMEOUT_MS,
                By.cssSelector("input[type='password']"),
                By.xpath("//input[@name='password']"),
                By.xpath("//form//input[@type='password']")
        );
        el.clear();
        el.sendKeys(password);
    }

    public void clickRegister() {
        removeOverlaysIfAny();
        WebElement btn = findVisibleElement(DEFAULT_TIMEOUT_MS,
                By.xpath("//button[contains(normalize-space(.),'Зарегистрир') or contains(normalize-space(.),'Зарегистрироваться')]"),
                By.xpath("//button[contains(.,'Зарегистр')]"),
                By.cssSelector("button.button_button")
        );
        safeClick(btn);
    }

    // --- Helpers ---
    private WebElement findVisibleElement(long timeoutMs, By... locators) {
        long end = System.currentTimeMillis() + timeoutMs;
        while (System.currentTimeMillis() < end) {
            for (By loc : locators) {
                try {
                    List<WebElement> list = driver.findElements(loc);
                    for (WebElement e : list) {
                        if (e.isDisplayed() && e.isEnabled()) {
                            return e;
                        }
                    }
                } catch (Exception ignored) { }
            }
            try { Thread.sleep(200); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
        }
        throw new org.openqa.selenium.TimeoutException("Не найден видимый элемент по переданным локаторам");
    }

    private void safeClick(WebElement el) {
        try {
            el.click();
        } catch (ElementClickInterceptedException | WebDriverException ex) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", el);
            } catch (Exception ignored) { }
        }
    }

    private void removeOverlaysIfAny() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "document.querySelectorAll(\"div[class*='Modal_modal'], div[class*='Modal_modal_overlay'], div[class*='Modal_overlay'], div[class*='overlay']\").forEach(function(e){e.remove();});"
            );
            Thread.sleep(150);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        } catch (Exception ignored) { }
    }
}