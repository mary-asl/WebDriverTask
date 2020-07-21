import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;

public class BaseForAllTests {

    protected WebDriver driver;
    protected String baseUrl = "https://www.wildberries.kz";

    @BeforeClass
    public void initWebDriver() throws InterruptedException {
        System.setProperty("webdriver.gecko.driver", "./src/main/resources/gecko/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.get(baseUrl); // Open the www.wildberries.kz. in the FireFox browser
    }
}
