import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyJunit {
    WebDriver driver;

    @BeforeAll
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @DisplayName("Check if site title is showing")
    @Test
    public void getTitle() {
        driver.get("https://demoqa.com/");
        String titleActual = driver.getTitle();
        String expectedResult = "DEMOQA";
        Assertions.assertEquals(titleActual, expectedResult);
    }

    @DisplayName("Check if title image is showing")
    @Test
    public void getImage() {
        driver.get("https://demoqa.com/");
        boolean status = driver.findElements(By.tagName("img")).get(1).isDisplayed();
        Assertions.assertTrue(status);
    }

    @DisplayName("Check if banner poster is showing")
    @Test
    public void checkBannerImage() {
        driver.get("https://demoqa.com/");
        boolean bannerStatus = driver.findElement(By.className("banner-image")).isDisplayed();
        Assertions.assertTrue(bannerStatus);
    }

    @DisplayName("Check if form is submitted properly")
    @Test
    public void formSubmisson() throws InterruptedException {
        driver.get("https://demoqa.com/text-box");
        driver.findElement(By.id("userName")).sendKeys("Shishir Saha");
        driver.findElement(By.id("userEmail")).sendKeys("Shishirsaha1530@gmail.com");
        driver.findElement(By.id("currentAddress")).sendKeys("Konabari, Gazipur");
        driver.findElement(By.id("permanentAddress")).sendKeys("Konabari, Gazipur");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 350)");

        Thread.sleep(5000);
        driver.findElement(By.id("submit")).click();

        String actualName = driver.findElement(By.id("name")).getText();
        Assertions.assertTrue(actualName.contains("Saha"));

    }

    @DisplayName("Get multiple elements using same className")
    @Test
    public void automateForm() throws InterruptedException {
        driver.get("https://demoqa.com/text-box");

        List<WebElement> formElement = driver.findElements(By.className("form-control"));
        formElement.get(0).sendKeys("Shishir Saha");
        formElement.get(1).sendKeys("Shishirsaha1530@gmail.com");
        formElement.get(2).sendKeys("Konabari, Gazipur");
        formElement.get(3).sendKeys("Konabari, Gazipur");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 350)");

        Thread.sleep(5000);

        List<WebElement> btnElement = driver.findElements(By.cssSelector("[type=button]"));
        btnElement.get(1).click();
    }

    @DisplayName("Click Button to see alert")
    @Test
    public void alertbtnHandling() {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.xpath("//button[@id='alertButton']")).click();
        driver.switchTo().alert().accept();
    }

    @DisplayName("On button click, alert will appear after 5 seconds")
    @Test
    public void alertBtnLateHandling() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.xpath("//button[@id='timerAlertButton']")).click();
        Thread.sleep(5000);
        driver.switchTo().alert().accept();
    }

    @DisplayName("On button click, confirm box will appear")
    @Test
    public void alertDismissCheck() {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.xpath("//button[@id='confirmButton']")).click();
        driver.switchTo().alert().dismiss();
    }

    @DisplayName("On button click, prompt box will appear")
    @Test
    public void alertPromptBox() {
        driver.get("https://demoqa.com/alerts");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 350)");
        driver.findElement(By.xpath("//button[@id='promtButton']")).click();
        driver.switchTo().alert().sendKeys("Shishir Saha");

        driver.switchTo().alert().accept();

        String actualText = driver.findElement(By.xpath("//span[@id='promptResult']")).getText();
        Assertions.assertTrue(actualText.contains("Shishir"));

    }

    @DisplayName("Date Picket testing")
    @Test
    public void keyboardEventWithdatePicker() throws InterruptedException{
        driver.get("https://demoqa.com/date-picker");
        WebElement txt = driver.findElement(By.id("datePickerMonthYearInput"));
        txt.click();
        txt.sendKeys(Keys.CONTROL+"a");
        txt.sendKeys(Keys.BACK_SPACE);
        Thread.sleep(5000);
        txt.sendKeys("10/02/2023");
        txt.sendKeys(Keys.ENTER);
    }

    @DisplayName("Drop Down Handling")
    @Test
    public void singleDropDown() throws InterruptedException{
        driver.get("https://demoqa.com/select-menu");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
        Select select = new Select(driver.findElement(By.xpath("//select[@id='oldSelectMenu']")));
        Thread.sleep(2000);
        select.selectByVisibleText("Green");
        Thread.sleep(2000);
        select.selectByValue("2");
        Thread.sleep(2000);
        select.selectByIndex(4);
    }


    @DisplayName("Drop Down Handling")
    @Test
    public void multipleSelectValue() throws InterruptedException{
        driver.get("https://demoqa.com/select-menu");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");

        Select multiSelect = new Select(driver.findElement(By.xpath("//select[@id='cars']")));
        if (multiSelect.isMultiple()) {
            multiSelect.selectByVisibleText("Saab");
            Thread.sleep(3000);
            multiSelect.selectByVisibleText("Opel");
        }
    }





    @AfterAll
    public void tearDown() {
        driver.quit();
    }

}
