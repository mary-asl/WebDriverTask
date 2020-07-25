import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class EksmoPageTest extends BaseForAllTests {

    private static final By BTN_NEXT_LOCATOR = By.xpath("//div[@class='brands-pane j-brands-slider-wrapper i-slider-brand-pane']//a[@class='lSNext']/button");
    private static final By CATEGORY_PSYCHOLOGY_LOCATOR = By.xpath("//div[@class='small-banners']/div[@class='number-5']");
    private static final By BOOK_GENRE_LOCATOR = By.xpath("//div[@class='params']//div//span[b='Жанры/тематика']/following::span[1]");
    private static final By SALES_LOCATOR = By.xpath("//div/a[@id='sale']/span");
    private static final By RATE_LOCATOR = By.cssSelector("#rate");
    private static final By ITEMS_DISCOUNT_LOCATOR = By.cssSelector("span.price-sale.active");
    private static final By ITEM_PRICES_LOCATOR = By.xpath("//ins[@class='lower-price']");
    private static final By ITEM_LOCATOR = By.xpath("//div[@class='dtList-inner']");
    private static final By ITEM_RATES_LOCATOR = By.xpath("//span[@class='dtList-comments-count c-text-sm']");

    @Test
    public void filterByDiscount() {
        boolean actual = false;
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,4700)");
        WebElement eksmo = driver.findElement(By.xpath("//a[@href='/brands/eksmo']"));
        waitForElementVisible(BTN_NEXT_LOCATOR);
        WebElement buttonNext = driver.findElement(BTN_NEXT_LOCATOR);
        int elementPosition = buttonNext.getLocation().getY();
        ((JavascriptExecutor) driver).executeScript(String.format("window.scroll(0, %s)", elementPosition));
        do {
            buttonNext.click();
        }
        while (!eksmo.isDisplayed());
        eksmo.click();
        driver.findElement(CATEGORY_PSYCHOLOGY_LOCATOR).click();
        driver.findElement(SALES_LOCATOR).click();
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,200)");
        waitForElementVisible(ITEMS_DISCOUNT_LOCATOR);
        List<WebElement> sales = driver.findElements(ITEMS_DISCOUNT_LOCATOR);

        for (int i = 0; i < sales.size(); i++) {
            for (int j = i + 1; j < sales.size(); j++) {
                Double sale1 = Double.parseDouble(sales.get(i).getText().substring(0, 3));
                Double sale2 = Double.parseDouble(sales.get(j).getText().substring(0, 3));
                if (sale1 > sale2) {
                    actual = false;
                    break;
                } else
                    actual = true;
            }
        }
        Assert.assertTrue(actual);
    }

    @Test
    public void filterByRate() {
        boolean actual = false;
        waitForElementVisible(RATE_LOCATOR);
        driver.findElement(RATE_LOCATOR).click();
        List<WebElement> rates = driver.findElements(ITEM_RATES_LOCATOR);

        for (int i = 0; i < rates.size(); i++) {
            for (int j = i + 1; j < rates.size(); j++) {
                Integer rate1 = Integer.parseInt(rates.get(i).getText());
                Integer rate2 = Integer.parseInt(rates.get(j).getText());
                if (rate1 < rate2) {
                    actual = false;
                    break;
                } else
                    actual = true;
            }
        }
        Assert.assertTrue(actual);
    }

    @Test
    public void filterByPrice() {
        boolean actual = false;
        WebDriverWait wait = new WebDriverWait(driver, 15);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#price")));
        element.click();
        waitForElementVisible(ITEM_PRICES_LOCATOR);
        List<WebElement> prices = driver.findElements(ITEM_PRICES_LOCATOR);

        for (int i = 0; i < prices.size(); i++) {
            for (int j = i + 1; j < prices.size(); j++) {
                Integer price1 = Integer.parseInt(StringUtils.substringBefore(prices.get(i).getText().replaceAll("\\s+", ""), "тг"));
                Integer price2 = Integer.parseInt(StringUtils.substringBefore(prices.get(j).getText().replaceAll("\\s+", ""), "тг"));
                if (price1 > price2) {
                    actual = false;
                    break;
                } else actual = true;
            }
        }
        Assert.assertTrue(actual);
    }

    @Test
    public void verifyDisplayedCategory() {
        driver.navigate().to("https://www.wildberries.kz/brands/eksmo");
        boolean actual = driver.findElement(By.id("newsSmallBanners")).isDisplayed();
        Assert.assertTrue(actual);
    }

    @Test
    public void isCategoryCorrect() {
        waitForElementVisible(ITEM_LOCATOR);
        driver.findElement(ITEM_LOCATOR).click();
        WebElement readAllInformation = driver.findElement(By.xpath("//span//div[@class='for-link']/div[@class='c-link-in3-v1']"));
        int elementPosition2 = readAllInformation.getLocation().getY();
        String js2 = String.format("window.scroll(0, %s)", elementPosition2);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        ((JavascriptExecutor) driver).executeScript(js2);
        readAllInformation.click();
        String actual = driver.findElement(BOOK_GENRE_LOCATOR).getText();
        String expected = "Психология";
        Assert.assertEquals(actual, expected);
    }

}
