import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PromoActionsTest extends BaseForAllTests {

    private static final By PROMO_PAGE_LOCATOR = By.xpath("//a[@href='/promotions/chistim-sklad?bid=eaba7351-8486-4110-a9c0-5ff541286f56']");
    private static final By ITEMS_LOCATOR = By.xpath("//div[@class='dtList-inner']");
    private static final By SIZE_BTN_LOCATOR = By.xpath("//label[@data-size-name]");

    @Test
    public void verifyDisplayedItems() {
        boolean actual = driver.findElement(ITEMS_LOCATOR).isDisplayed();
        Assert.assertEquals(actual, true);
    }

    @Test()
    public void verifyDiscount() {
        boolean actual = true;
        WebElement linkToPromoPage = driver.findElement(PROMO_PAGE_LOCATOR);
        do {
            driver.findElement(By.xpath("//div/a/button[@class='btn-next']")).click();
        }
        while (!linkToPromoPage.isDisplayed());
        linkToPromoPage.click();
        List<WebElement> discounts = driver.findElements(By.cssSelector("span.price-sale.active"));
        for (WebElement discount : discounts) {
            Double discountNum = Double.parseDouble(discount.getText().substring(0, 3));
            if (discountNum > -50) {
                actual = false;
                break;
            }
        }
        Assert.assertEquals(actual, true);
    }

    @Test()
    public void verifyFavorites() {
        driver.findElement(ITEMS_LOCATOR).click();
        boolean check;
        try {
            int elementPosition = driver.findElement(SIZE_BTN_LOCATOR).getLocation().getY();
            String js = String.format("window.scroll(0, %s)", elementPosition);
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            ((JavascriptExecutor) driver).executeScript(js);
            check = true;
        } catch (NoSuchElementException e) {
            check = false;
        }
        if (check)
            driver.findElement(SIZE_BTN_LOCATOR).click();
        driver.findElement(By.xpath("//div[@class='order']/button")).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".signIn")));
        boolean actual = element.isDisplayed();
        Assert.assertTrue(actual);
    }
}
