import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class action {

    protected WebDriver driver;
    int DefaultTime = 60;

    public List<WebElement> getWebElements(Locator locator) {
        waitUntilDisplayed(locator,5);
        List<WebElement> webElementList = driver.findElements(locator.getBy());
        if (webElementList.isEmpty()) {
            Reporter.log("Viewed all list of " + locator.getName() + "");
        } else {
            Reporter.log("Unable to view list of " + locator.getName() + "");
        }
        return webElementList;
    }


    protected void click(Locator locator) throws InstantiationException, IllegalAccessException {

        WebDriverWait webdriverWait = new WebDriverWait(driver, DefaultTime);
        webdriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator.getBy()));
        driver.findElement(locator.getBy()).click();
        Reporter.log("Clicked On " + locator.getName() + "",true);
    }

    protected String getText(List<WebElement> elements, int index){
        String Text = "No Data";
        try {
            Text = elements.get(index).getText();
            return Text;
        }catch (Exception e){
            Reporter.log(e.toString());
            return Text;
        }
    }

        protected  boolean waitUntilDisplayed(Locator locator, int Timeout) {
            driver.manage().timeouts().implicitlyWait(Timeout, TimeUnit.SECONDS);
            WebDriverWait ww = new WebDriverWait(driver, Timeout);
            try {
                ww.until(ExpectedConditions.elementToBeClickable(locator.getBy()));
                return true;
            } catch (Exception e) {
                return false;
            } finally {
                driver.manage().timeouts().implicitlyWait(DefaultTime, TimeUnit.SECONDS);
            }
        }


        protected void mouseOver(Locator locator) throws InterruptedException {
            try {
                waitUntilDisplayed(locator,4);
                Actions action = new Actions(driver);
                WebElement webElement = driver.findElement(locator.getBy());
                action.moveToElement(webElement).build().perform();
                Thread.sleep(1000);
                action.moveToElement(webElement).build().perform();
                Thread.sleep(1000);
                Reporter.log("Hovered mouse on " + locator.getName() + "",true);
            } catch (Exception e) {
                System.out.println(e.toString());
                Reporter.log("Unable to hover " + locator.getName() + "",true);

            }
        }

    protected void waitforPageReady() {

        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver browser) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        WebDriverWait wait = new WebDriverWait(driver, 60);
        try {
            wait.until(expectation);
        } catch (Throwable error) {
            error.printStackTrace();

        }
    }
}
