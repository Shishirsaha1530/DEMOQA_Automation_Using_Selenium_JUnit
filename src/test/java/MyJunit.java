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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


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
    public void keyboardEventWithdatePicker() throws InterruptedException {
        driver.get("https://demoqa.com/date-picker");
        WebElement txt = driver.findElement(By.id("datePickerMonthYearInput"));
        txt.click();
        txt.sendKeys(Keys.CONTROL + "a");
        txt.sendKeys(Keys.BACK_SPACE);
        Thread.sleep(5000);
        txt.sendKeys("10/02/2023");
        txt.sendKeys(Keys.ENTER);
    }

    @DisplayName("Drop Down Handling")
    @Test
    public void singleDropDown() throws InterruptedException {
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
    public void multipleSelectValue() throws InterruptedException {
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

    @DisplayName("MultiSelect Drop Down Handling")
    @Test
    public void multiSelectDropDownHandling() throws InterruptedException {
        driver.get("https://demoqa.com/select-menu");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,300)");

        driver.findElements(By.className("css-1hwfws3")).get(2).click();

        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ARROW_DOWN).perform();
        Thread.sleep(5000);
        actions.sendKeys(Keys.ENTER).perform();
    }

    @DisplayName("Mouse Hover Handling")
    @Test
    public void mouseHoveHandling(){
        driver.get("https://green.edu.bd/");
        WebElement lblMenu = driver.findElement(By.xpath("//a[@class='dropdown-toggle'][normalize-space()='About Us']"));

        Actions actions = new Actions(driver);
        actions.moveToElement(lblMenu).perform();
    }


    @DisplayName("Modal Testing")
    @Test
    public void modalDialog(){
    driver.get("https://demoqa.com/modal-dialogs");
    driver.findElement(By.id("showSmallModal")).click();
    String text= driver.findElement(By.className("modal-body")).getText();
    System.out.println(text);
    driver.findElement(By.id("closeSmallModal")).click();
    }


    @DisplayName("Upload and download Image Testing")
    @Test
    public void uploadDownloadImage() throws InterruptedException{
    driver.get("https://demoqa.com/upload-download");

    driver.findElement(By.id("uploadFile")).sendKeys(System.getProperty("user.dir")+"/src/test/resources/BugOne.png");

    String filepath = driver.findElement(By.id("uploadedFilePath")).getText();
    Assertions.assertTrue(filepath.contains("BugOne.png"));

    driver.findElement(By.xpath("//a[@id='downloadButton']")).click();
    Thread.sleep(40000);

    }

    @DisplayName("Button handling")
    @Test
    public void btnHandling(){
        driver.get("https://demoqa.com/buttons");
        List<WebElement> buttonElements = driver.findElements(By.className("btn-primary"));

        Actions actions = new Actions(driver);
        actions.doubleClick(buttonElements.get(0)).perform();
        actions.contextClick(buttonElements.get(1)).perform();
        actions.click(buttonElements.get(2)).perform();

        List<WebElement> txtElements = driver.findElements(By.tagName("p"));

        String txt1 = txtElements.get(0).getText();
        String txt2 = txtElements.get(1).getText();
        String txt3 = txtElements.get(2).getText();

        Assertions.assertTrue(txt1.contains("double click"));
        Assertions.assertTrue(txt2.contains("right click"));
        Assertions.assertTrue(txt3.contains("dynamic click"));

    }

    @DisplayName("New Tab handling")
    @Test

    public void tabHandling() throws InterruptedException{
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.cssSelector("#tabButton")).click();
        Thread.sleep(4000);
        ArrayList<String> w = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(w.get(1));
        String text = driver.findElement(By.id("sampleHeading")).getText();
        Assertions.assertEquals(text, "This is a sample page");
        driver.close();
        driver.switchTo().window(w.get(0));
    }

  
    @DisplayName("Multiple Window handling")
    @Test
    public void multipleWinHandling(){
        driver.get("https://demoqa.com/browser-windows");

        driver.findElement(By.id(("windowButton"))).click();
        String mainWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        java.util.Iterator<String> iterator = allWindowHandles.iterator();
       
        while (iterator.hasNext()) {
        String ChildWindow = iterator.next();
        if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {
        driver.switchTo().window(ChildWindow);
        String text= driver.findElement(By.id("sampleHeading")).getText();
        Assertions.assertTrue(text.contains("This is a sample page"));
    }
    }
    }
    @AfterAll
    public void tearDown() {
        driver.quit();
    }

}
