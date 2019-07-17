import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SecondTest {
    WebDriver driver;

    @Test
    public void test() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.get("http://www.sberbank.ru/ru/person");

        Thread.sleep(5000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("return window.stop");


        //страхование
        WebElement spanBtn = driver.findElement(By.xpath("//span[contains(text(), 'Страхование')]"));
        spanBtn.click();

        //Путешествия и покупки
        WebElement strBtn = driver.findElement(By.xpath("//li[contains(@class, 'lg-menu__sub-item')]/a[contains(text(), 'Путешествия и покупки')]"));
        strBtn.click();

        //оформить онлайн
        WebElement onlineBtn = driver.findElement(By.xpath("//div[contains(@class, 'sbrf-rich-outer')]/following::a[1]"));
        onlineBtn.click();


        //ВОТ ТУТ Я СОВСЕМ ЗАПУТАЛАСЬ
        //driver.switchTo().window(driver.getWindowHandle());


        //WebDriverWait wait = new WebDriverWait(driver, 5, 200);
        //wait.until(ExpectedConditions.elementToBeClickable(By.className("lg-menu__sub")));
        //wait.until(ExpectedConditions.);

        //минимальная
        WebElement minCheck = driver.findElement(By.xpath("//div[contains(@class, 'b-form-prog-box b-form-active-box')]"));
        minCheck.click();

        driver.close();
        driver.quit();
    }
}
