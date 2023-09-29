import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

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
    
     @AfterAll
    public void tearDown() {
        driver.quit();
    }


}
