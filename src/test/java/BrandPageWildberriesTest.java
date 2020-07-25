import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class BrandPageWildberriesTest extends BaseForAllTests {

    @Test
    public void verifyDisplayedCategory() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,4700)");
        WebElement eksmo = driver.findElement(By.xpath("//a[@href='/brands/eksmo']"));
        WebElement buttonNext = driver.findElement(By.xpath("//div[9]//a[2]/button[@class='btn-next']"));
        int elementPosition = buttonNext.getLocation().getY();
        String js = String.format("window.scroll(0, %s)", elementPosition);
        ((JavascriptExecutor) driver).executeScript(js);
        do {
            buttonNext.click();
        }
        while (!eksmo.isDisplayed());
        eksmo.click();
        boolean actual = driver.findElement(By.id("newsSmallBanners")).isDisplayed();
        Assert.assertTrue(actual);
    }

    @Test(dependsOnMethods = "verifyDisplayedCategory")
    public void isCategoryCorrect() {
        driver.findElement(By.xpath("//div[@class='dtList-inner']")).click();
        WebElement readAllInformation = driver.findElement(By.xpath("//span//div[@class='for-link']/div[@class='c-link-in3-v1']"));
        int elementPosition = readAllInformation.getLocation().getY();
        String js = String.format("window.scroll(0, %s)", elementPosition);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        ((JavascriptExecutor) driver).executeScript(js);
        readAllInformation.click();
        String actual = driver.findElement(By.xpath("//div[@class='params']//div//span[b='Жанры/тематика']/following::span[1]")).getText();
        String expected = "Психология";
        Assert.assertEquals(actual, expected);
        driver.navigate().back();
    }

    @Test(dependsOnMethods = "verifyDisplayedCategory")
    public void filterByRate() {
        driver.findElement(By.xpath("//div[@class='small-banners']/div[5]")).click();
        boolean actual = true;
        WebDriverWait wait = new WebDriverWait(driver, 15);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#rate")));
        element.click();
        List<WebElement> rates = driver.findElements(By.xpath("//div//span/a/span/span/span[2]"));

        for (int i = 0; i < rates.size(); i++) {
            for (int j = i + 1; j < rates.size(); j++) {
                Integer rate1 = Integer.parseInt(rates.get(i).getText());
                Integer rate2 = Integer.parseInt(rates.get(j).getText());
                if (rate1 < rate2) {
                    actual = false;
                    break;
                }
            }
        }
        Assert.assertTrue(actual);
    }

    @Test(dependsOnMethods = "verifyDisplayedCategory", priority = 1)
    public void filterByPrice() {
        boolean actual = true;
        WebDriverWait wait = new WebDriverWait(driver, 15);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#price")));
        element.click();
        List<WebElement> prices = driver.findElements(By.xpath("//div//span/a//div[2]/span/ins"));

        for (int i = 0; i < prices.size(); i++) {
            for (int j = i + 1; j < prices.size(); j++) {
                Integer price1 = Integer.parseInt(StringUtils.substringBefore(prices.get(i).getText().replaceAll("\\s+", ""), "тг"));
                Integer price2 = Integer.parseInt(StringUtils.substringBefore(prices.get(j).getText().replaceAll("\\s+", ""), "тг"));
                if (price1 > price2) {
                    actual = false;
                    break;
                }
            }
        }
        Assert.assertTrue(actual);
    }

    @Test(dependsOnMethods = "filterByPrice")
    public void filterByDiscount() {
        boolean actual = true;
        WebDriverWait wait = new WebDriverWait(driver, 15);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[4]/span")));
        element.click();
        List<WebElement> sales = driver.findElements(By.xpath("span.price-sale.active"));

        for (int i = 0; i < sales.size(); i++) {
            for (int j = i + 1; j < sales.size(); j++) {
                Double sale1 = Double.parseDouble(sales.get(i).getText().substring(0, 3));
                Double sale2 = Double.parseDouble(sales.get(j).getText().substring(0, 3));
                if (sale1 > sale2) {
                    actual = false;
                    break;
                }
            }
        }
        Assert.assertTrue(actual);
    }
}
