import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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


        //выбрать сумму страховой защиты ??
        ////div[contains(@class, 'b-form-prog-box b-form-active-box')]
        waitSectionToDownload();

        //оформить
        clickBtn("//span[contains(text(), 'Оформить')]");


        fillInputBySection("1", "insured0_surname", "Gorbulina");

//        WebElement srnLine = driver.findElement(By.xpath("//input[contains(@name, 'insured0_surname')]"));
//        String text1 = "Gorbulina";
//        srnLine.sendKeys(text1);





//        InputPaspot("Серия", "123456");
//        InputPaspot("Номер", "123456");
//        fillInputBySection("issueDate", "12052014");
//        InputPaspot("Кем выдан", "фывафыва");


        clickBtn("//span[contains(text(), 'Продолжить')]");

//        Assert.assertTrue(driver.findElement(By.xpath("//*[text() = 'Заполнены не все обязательные поля']")).isEnabled());

        checkError("phone", "некорректные данные");

        driver.close();
        driver.quit();
    }

    public void clickBtn(String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

    public String fillInputBySection(String numbOfSection, String name, String textToFill) {
        String path = String.format("//section[contains(@class, 'b-form-section')][%s]//input[contains(@name, '%s')]");
        driver.findElement(By.xpath(path)).sendKeys(textToFill);
        return driver.findElement(By.xpath(path)).getAttribute("value placeholder");
//        String temp = "//*[contains(@name, '$s')]";
//        String fulltext = String.format(temp, name);
//        driver.findElement(By.xpath(fulltext)).sendKeys(textToFill);
    }

//    public String InputPaspot(String section, String name, String textToFill) {
//        String path;
//        if (name.equals("Имя") || name.equals("Серия") ||name.equals("Номер")) {
//            return fillInputBySection(section, name, textToFill);
//
//
//        driver.findElement(By.xpath(path)).sendKeys(textToFill);
//        return driver.findElement(By.xpath(path)).getAttribute("value placeholder");


////        String temp = "//*[contains(@placeholder, '$s')]";
////        String fulltext = String.format(temp, name);
////        driver.findElement(By.xpath(fulltext)).sendKeys(textToFill);
//    }

    public void checkError(String name, String expectedText) {
        String template = "//input[contains(@name, 'phone')]/following::span[contains(@class, 'b-text-field-error')]";
        String fullxpath = String.format(template, name);
        String actualText = driver.findElement(By.xpath(fullxpath)).getText();

        Assert.assertEquals(expectedText, actualText);
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
