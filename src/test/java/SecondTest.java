import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SecondTest {
    WebDriver driver;

    @Test
    public void test() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("start-maximized");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://www.sberbank.ru/ru/person");


        Thread.sleep(5000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("return window.stop");


        //страхование
        clickBtn("//span[contains(text(), 'Страхование')]");


        //Путешествия и покупки
        clickBtn("//li[contains(@class, 'lg-menu__sub-item')]/a[contains(text(), 'Путешествия и покупки')]");


        //оформить онлайн

        Set<String> h1 = driver.getWindowHandles();
        driver.findElement(By.xpath("//div[contains(@class, 'sbrf-rich-outer')]/following::a[1]")).click();
        Set<String> h2 = driver.getWindowHandles();
        h2.removeAll(h1);
        String h3 = h2.iterator().next();
        driver.switchTo().window(h3);


        waitSectionToDownload();

        clickBtn("//section[contains(@class, 'b-form-section')][2]//div[contains(@class, 'b-form-prog-box')][1]");

        //оформить
        clickBtn("//span[contains(text(), 'Оформить')]");


        fillInputBySection("1", "insured0_surname", "Gorbulina");
        fillInputBySection("1", "insured0_name", "Alesya");
        fillInputBySection("1", "insured0_birthDate", "28111997");
        fillInputBySection("2", "surname", "Русанова");
        driver.findElement(By.xpath("//section[contains(@class, 'b-form-section')][2]//input[contains(@placeholder, 'Имя')]")).sendKeys("Мария");
        //Comments("2", "Имя", "Мария");
        //fillInputBySection("2", "name", "Мария");
        fillInputBySection("2", "middlename", "Леонидовна");
        fillInputBySection("2", "birthDate", "14071997");
        clickBtn("//input[contains(@name, 'female')]");
        fillInputBySection("3", "passport_series", "1234");
        fillInputBySection("3", "passport_number", "123456");
        fillInputBySection("3", "issueDate", "20102015");
        Comments("3", "Кем выдан","В ЦАО гор. Москвы");


        clickBtn("//span[contains(text(), 'Продолжить')]");

        Assert.assertTrue(driver.findElement(By.xpath("//*[text() = 'Заполнены не все обязательные поля']")).isEnabled());


        driver.close();
        driver.quit();
    }

    public void clickBtn(String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

    public String fillInputBySection(String numbOfSection, String name, String textToFill) {
        String path = String.format("//section[contains(@class, 'b-form-section')][%s]//input[contains(@name, '%s')]", numbOfSection, name);
        driver.findElement(By.xpath(path)).sendKeys(textToFill);
        return driver.findElement(By.xpath(path)).getAttribute("value placeholder");
    }

    public String Comments(String numbOfSection, String placeholder, String text) {
       /* if(placeholder.equals("Имя")){
         path = String.format("//section[contains(@class, 'b-form-section')][%s]//input[contains(@placeholder, '%s')]", numbOfSection, placeholder);
        } else if (placeholder.equals("Кем выдан")){*/
            String path = String.format("//section[contains(@class, 'b-form-section')][%s]//textarea[contains(@placeholder, '%s')]", numbOfSection, placeholder);
       // }
        //String path = String.format("//textarea[contains(@name, '%s')]", name);
        driver.findElement(By.xpath(path)).sendKeys(text);
        return driver.findElement(By.xpath(path)).getAttribute("value");
    }


    public void waitSectionToDownload() {
        WebDriverWait wait = new WebDriverWait(driver, 5000, 200);
        wait.until(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(By.xpath("//section[contains(@class, 'b-active-tab')]")), "class"));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//section[contains(@class, 'b-form-section')][2]"))));
        WebElement myElement = driver.findElement(By.xpath("//section[contains(@class, 'b-active-tab')]"));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView()", myElement);
    }

}
