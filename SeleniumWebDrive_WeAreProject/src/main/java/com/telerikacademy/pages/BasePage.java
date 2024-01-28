package com.telerikacademy.pages;

import com.telerikacademy.testframework.UserActions;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.telerikacademy.testframework.Utils.*;
import static java.lang.String.format;

public abstract class BasePage {

    public static UserActions actions;
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected String url;

    public BasePage(WebDriver driver, String urlKey) {
        String pageUrl = getConfigPropertyByKey(urlKey);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.url = pageUrl;
        actions = new UserActions();
    }


    public static void assertElementPresent(String locator) {
        actions.waitForElementVisible(locator);
        Assertions.assertNotNull(driver.findElement(By.xpath(getUIMappingByKey(locator))),
                format("Element with %s doesn't present.", locator));
    }


    public void navigateToPage() {
        this.driver.get(url);
    }

    public void assertNavigatedUrl() {
        String currentUrl = driver.getCurrentUrl();
        LOGGER.info(format("Landed URL: %s", currentUrl));
        Assertions.assertTrue(currentUrl.contains(url), "Landed URL is not as expected. Actual URL: " + currentUrl + ". Expected URL: " + url);
    }
}
