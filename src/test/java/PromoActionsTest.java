import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PromoActionsTest extends BaseForAllTests {

    private static final By PROMO_PAGE_LOCATOR = By.xpath(".//a[contains(@href,'/promotions/chistim-sklad')]");
    private static final By ITEMS_LOCATOR = By.xpath("//div[@class='dtList-inner']");
    private static final By SIZE_BTN_LOCATOR = By.xpath("//label[@data-size-name]");
    private static final By SIGN_IN_FORM = By.cssSelector(".signIn");

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
        WebElement size = driver.findElement(SIZE_BTN_LOCATOR);
        int elementPosition = size.getLocation().getY();
        String js = String.format("window.scroll(0, %s)", elementPosition);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        ((JavascriptExecutor) driver).executeScript(js);
        if (size.isDisplayed())
            driver.findElement(SIZE_BTN_LOCATOR).click();
        driver.findElement(By.xpath("//div[@class='order']/button")).click();
        waitForElementVisible(SIGN_IN_FORM);
        boolean actual = driver.findElement(SIGN_IN_FORM).isDisplayed();
        Assert.assertTrue(actual);
    }
}
