import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TextSearchWildberriesTest extends BaseForAllTests {

    private static final By ITEMS_NAME_LOCATOR = By.xpath("//span[@class='goods-name c-text-sm']");
    private static final By SEARCH_INPUT_LOCATOR = By.id("tbSrch");
    private static final By SEARCH_BTN_LOCATOR = By.id("btnSrch");
    private static final By PAGE_TITLE_LOCATOR = By.xpath("/html/head/title");

    @Test
    public void verifyPageTitle() {
        WebElement input = driver.findElement(SEARCH_INPUT_LOCATOR);
        String expectedTitle = input.getText();
        String actualTitle = driver.findElement(PAGE_TITLE_LOCATOR).getText();
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @Test()
    public void isItemFound() {
        boolean actual = true;
        WebElement input = driver.findElement(SEARCH_INPUT_LOCATOR);
        input.sendKeys("funko pop star wars");
        driver.findElement(SEARCH_BTN_LOCATOR).click();
        String inputValue = driver.findElement(SEARCH_INPUT_LOCATOR).getAttribute("value");
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,200)");
        waitForElementVisible(ITEMS_NAME_LOCATOR);
        String searchingItemName = driver.findElement(ITEMS_NAME_LOCATOR).getText();
        String[] itemNameByWords = inputValue.split(" ");
        for (int i = 0; i < itemNameByWords.length; i++) {
            if (!StringUtils.containsIgnoreCase(searchingItemName, itemNameByWords[i])) {
                actual = false;
                break;
            }
        }
        Assert.assertTrue(actual);
    }
}
