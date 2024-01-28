package weare.testing;


import com.telerikacademy.pages.BasePage;
import org.openqa.selenium.WebDriver;

public abstract class BaseWeArePage extends BasePage {
    public BaseWeArePage(WebDriver driver, String urlKey) {
        super(driver, urlKey);
    }
}
