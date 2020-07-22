import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class FirstScript extends BaseForAllTests {

    @Test
    public void verifyDisplayedItems() { // Verify that items are displayed
        WebElement banner = driver.findElement(By.id("banner_12529ee6-9afc-449a-b568-6fdfba574386"));
        do {
            driver.findElement(By.xpath("//div/a[2]/button")).click();
        }
        while (!banner.isDisplayed());
        banner.click(); // Click on the banner "50% discount"
        boolean actual = driver.findElement(By.xpath("//div[@class='dtList-inner']")).isDisplayed();
        Assert.assertEquals(actual, true);
    }

    @Test(dependsOnMethods = "verifyDisplayedItems") // Verify that all displayed items have discount since 50%
    public void verifyDiscount() {
        boolean actual = true;
        List<WebElement> discounts = driver.findElements(By.cssSelector("span.price-sale.active"));
        for (WebElement discount : discounts) {
            Double discountNum = Double.parseDouble(discount.getText().substring(0, 3));
            System.out.println(discountNum);
            if (discountNum > -50) {
                actual = false;
                break;
            }
        }
        Assert.assertEquals(actual, true);
    }

    @Test(dependsOnMethods = "verifyDisplayedItems")
    public void verifyFavorites() {
        driver.findElement(By.xpath("//div[@class='dtList-inner']")).click();
        boolean check;
        try {
            driver.findElement(By.xpath("//div[@class='i-sizes-block-v1']/label/span"));
            check = true;
        } catch (NoSuchElementException e) {
            check = false;
        }
        if (check)
            driver.findElement(By.xpath("//div[@class='i-sizes-block-v1']/label/span")).click(); //select size if it's possible;
        driver.findElement(By.xpath("//div[@class='order']/button")).click(); //add to favorites
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".signIn")));
        boolean actual = element.isDisplayed();
        Assert.assertTrue(actual);
    }
}
