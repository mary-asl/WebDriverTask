import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

public class BaseForAllTests {

    protected WebDriver driver;
    protected String baseUrl = "https://www.wildberries.kz";

    @BeforeClass
    public void initWebDriver() {
        System.setProperty("webdriver.gecko.driver", "./src/main/resources/gecko/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.get(baseUrl); // Open the www.wildberries.kz. in the FireFox browser
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterClass
    public void close(){
        driver.close();
    }
}
