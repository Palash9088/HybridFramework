package Base;//import java.util.*;

import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PredefinedActions {
    static WebDriver driver;
    static WebDriverWait wait;

    public static void openBrowser(String url, String browser) {
        System.out.println("STEP -> Opening Browser");
        switch (browser.toUpperCase()) {
            case "CHROME" -> {
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                driver = new ChromeDriver();
            }
            case "FIREFOX" -> {
                System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
                driver = new FirefoxDriver();
            }
            case "EDGE" -> {
                System.setProperty("webdriver.edge.driver", "src/main/resources/msedgedriver.exe");
                driver = new EdgeDriver();
            }
            default -> System.out.println("Wrong keyword");
        }
        driver.manage().window().maximize();
        System.out.println("STEP -> Opening Given " + url);
        driver.get(url);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    protected void hoverOnElement(WebElement target, boolean isWaitRequired) {
        Actions actions = new Actions(driver);
        if (isWaitRequired) {
            wait.until(ExpectedConditions.visibilityOf(target));
            actions.moveToElement(target).build().perform();
        } else {
            actions.moveToElement(target).build().perform();
        }
    }

    protected void switchToFrameByElement(WebElement element) {
        driver.switchTo().frame(element);
    }

    protected String getMainWindowHandleId() {
        return driver.getWindowHandle();
    }

    protected Set<String> getAllWindowHandlesId() {
        return driver.getWindowHandles();
    }

    protected void switchToSpecificWindow(String windowId) {
        driver.switchTo().window(windowId);
    }

    protected void clickOnElement(WebElement element) {
        element.click();
    }

    protected void clickOnElement(String locator, boolean isWaitRequired) {
        WebElement element = getElement(locator, isWaitRequired);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    protected void enterText(WebElement element, String text) {
        if (element.isEnabled())
            element.sendKeys(text);
        else
            element.sendKeys(text);
    }

    protected void enterText(String locator, boolean isWaitRequired, String textToBe) {
        WebElement element = getElement(locator, isWaitRequired);
        enterText(element, textToBe);
    }

    private String getLocatorType(String locator) {
        return locator.split("]:-")[0].substring(1);
    }

    private String getLocatorValue(String locator) {

        return locator.split("]:-")[1];
    }

    protected List<WebElement> getListOfWebElements(String locator, boolean isWaitRequired) {
        String locatorType = getLocatorType(locator);
        String locatorValue = getLocatorValue(locator);
        return driver.findElements(getByReference(locatorType, locatorValue));
    }

    protected List<String> getElementListText(String locator, boolean isWaitRequired) {
        //By byLocator = getByReference(getLocatorType(locator), getLocatorValue(locator));
        List<WebElement> elementList = getListOfWebElements(locator, isWaitRequired);
        List<String> elementListText = new ArrayList<>();
        for (WebElement element : elementList) {
            elementListText.add(element.getText());
        }
        return elementListText;
    }

    protected String getElementText(String locator, boolean isWaitRequired) {
        WebElement element = getElement(locator, isWaitRequired);
         return element.getText();
    }

    private By getByReference(String locatorType, String locatorValue) {
        switch (locatorType.toUpperCase()) {
            case "XPATH":
                return By.xpath(locatorValue);
            case "ID":
                return By.id(locatorValue);
            case "CLASSNAME":
                return By.className(locatorValue);
            case "PARTIALLINK":
                return By.partialLinkText(locatorValue);
            case "LINKTEXT":
                return By.linkText(locatorValue);
            case "CSS":
                return By.cssSelector(locatorValue);
            case "TAGNAME":
                return By.tagName(locatorValue);
            case "NAME":
                return By.name(locatorValue);

            default:
                System.out.println("Invalid Locator Type");
        }
        return null;
    }

    protected WebElement getElement(String locator, boolean isWaitRequired) {
        WebElement element = null;
        String locatorType = getLocatorType(locator);
        String locatorValue = getLocatorValue(locator);
        if (isWaitRequired)
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(getByReference(locatorType, locatorValue)));
        else
            driver.findElement(getByReference(locatorType, locatorValue));

        /*switch (locatorType.toUpperCase()) {
            case "XPATH":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
                else
                    driver.findElement(By.xpath(locatorValue));
                break;
            case "ID":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
                else
                    driver.findElement(By.id(locatorValue));
                break;
            case "CLASSNAME":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
                else
                    driver.findElement(By.className(locatorValue));
                break;
            case "LINKTEXT":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
                else
                    driver.findElement(By.linkText(locatorValue));
                break;
            case "PARTIAL LINKTEXT":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locatorValue)));
                else
                    driver.findElement(By.partialLinkText(locatorValue));
                break;
            case "CSS":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorValue)));
                else
                    driver.findElement(By.cssSelector(locatorValue));
                break;
            default:
                System.out.println("Invalid Locator");
        }*/
        return element;
    }


    protected void javaScriptClick(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
    }

    protected void openNewTab() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open();");
    }

    protected void navigateTo(String url) {
        driver.navigate().to(url);
    }

    public static void takeScreenshot(String filename) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        try {
            File file = new File("src/screenshots//" + filename + ".png");
            Files.copy(src, file);
        } catch (IOException e) {
            throw new RuntimeException("Did not captured screenshot");
        }
    }

    public static void closeBrowser() {
        driver.close();
    }
    public static void quitBrowser() {
        driver.close();
    }
}
