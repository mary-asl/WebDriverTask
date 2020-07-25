import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class PromoBannersWildberriesTest extends BaseForAllTests {

    @Test
    public void verifyDisplayedItems() {
        WebElement banner = driver.findElement(By.id("banner_323019f5-0db3-4d4d-95b1-b75fb62f21f2"));
        do {
            driver.findElement(By.xpath("//div/a[2]/button")).click();
        }
        while (!banner.isDisplayed());
        banner.click();
        boolean actual = driver.findElement(By.xpath("//div[@class='dtList-inner']")).isDisplayed();
        Assert.assertEquals(actual, true);
    }

    @Test(dependsOnMethods = "verifyDisplayedItems")
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
            driver.findElement(By.xpath("//div[@class='i-sizes-block-v1']/label/span")).click();
        driver.findElement(By.xpath("//div[@class='order']/button")).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".signIn")));
        boolean actual = element.isDisplayed();
        Assert.assertTrue(actual);
    }
}
