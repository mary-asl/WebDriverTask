import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ThirdScript extends BaseForAllTests {
    @Test
    public void verifyPageTitle() {
        WebElement input = driver.findElement(By.id("tbSrch"));
        String expectedTitle = input.getText();
        input.sendKeys("funko pop star wars");
        driver.findElement(By.id("btnSrch")).click();
        String actualTitle = driver.findElement(By.xpath("/html/head/title")).getText();
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @Test
    public void isItemFound() {
        boolean actual = driver.findElement(By.xpath("//div[@class='dtList-inner']")).isDisplayed();
        Assert.assertTrue(actual);
    }

}
