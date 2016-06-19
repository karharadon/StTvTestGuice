package lastochkin.streamTV.helpers;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import lastochkin.streamTV.pages.LoginPage;
import lastochkin.streamTV.pages.MainPage;
import lastochkin.streamTV.pages.ProfilePage;
import lastochkin.streamTV.tests.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.concurrent.TimeUnit;

public class GuiceTestModule implements Module {

    private final String browser = System.getProperty("browser");
    WebDriver driver;

    @Override
    public void configure(Binder binder) {
        binder.bind(LoginPage.class);
        binder.bind(ProfilePage.class);
        binder.bind(MainPage.class);
        binder.bind(ScreenShot.class);
        binder.bind(WebDriver.class).toInstance(getWebDriver());
    }

    @Singleton
    public WebDriver getWebDriver() {
        if (driver != null) {
            return driver;}
        else {
            if (browser.equals("chrome")) {
                driver = new ChromeDriver();
            }
            if (browser.equals("firefox")) {
                driver = new FirefoxDriver();
            }
            if (browser.equals("internetExplorer")) {
                driver = new InternetExplorerDriver();
            }
            if (driver != null) {
                driver.manage().timeouts().implicitlyWait(Long.parseLong(ConfigProperties.getProperty("impWait")),
                        TimeUnit.SECONDS);
                driver.manage().window().maximize();
            }
            return driver;
        }
    }
}

