import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    /*  driver.findElement(By.xpath("//div[@class='user-menu-item']")).click();
        WebElement phone = driver.findElement(By.xpath("//*[@id='phoneMobile']"));
        phone.sendKeys("7787101123");
        driver.findElement(By.xpath("//div[@class='i-form-block-v1']/div/button[@data-href]")).click();
        Thread.sleep(20000); */

    }
}
