package com.cc2;


import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;
public class AppTest 
{
    WebDriver driver;
    ExtentReports reports;
    Actions action;
    String url;
    @BeforeTest
    public void beforeTest()
    {
        WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver();
        String reportPath="C:\\Users\\karth.KARTHIKEYAN\\OneDrive\\Desktop\\Software Testing\\cc2\\src\\report\\result.html";
        reports = new ExtentReports();
        ExtentSparkReporter spark=new ExtentSparkReporter(reportPath);
        reports.attachReporter(spark);
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("Barnesandnoble");
        action=new Actions(driver);
        url="https://www.barnesandnoble.com/";
        driver.manage().window().maximize();
    }
    @Test(priority = 0)
    public void test1()
    {
        ExtentTest test1=reports.createTest("Test 1");
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.findElement(By.linkText("All")).click();
        driver.findElement(By.xpath("//*[@id=\"rhf_header_element\"]/nav/div/div[3]/form/div/div[1]/div/a[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"rhf_header_element\"]/nav/div/div[3]/form/div/div[2]/div/input[1]")).sendKeys("Chetan Bhagat");
        driver.findElement(By.xpath("//*[@id=\"rhf_header_element\"]/nav/div/div[3]/form/div/span/button")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        String headline = driver.findElement(By.xpath("//*[@id=\"searchGrid\"]/div/section[1]/section[1]/div/div[1]/div[1]/h1")).getText();
        if(headline.contains("Chetan Bhagat"))
        {
            test1.log(Status.PASS, "String found at the Headline");
        }
        else
        {
            test1.log(Status.FAIL, "String not found at the Headline");
        }
    }

    
    @Test(priority = 1)
    public void test2() throws InterruptedException
    {
        ExtentTest test2=reports.createTest("Test 2");
        driver.get(url);
        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        action.moveToElement(driver.findElement(By.xpath("//*[@id=\"rhfCategoryFlyout_Audiobooks\"]")))
        .perform();
        
        
        driver.findElement(By.xpath("//*[@id=\"navbarSupportedContent\"]/div/ul/li[5]/div/div/div[1]/div/div[2]/div[1]/dd/a[1]")).click();
        

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        
        WebElement element = driver.findElement(By.xpath("//*[@id=\"rhfCategoryFlyout_Audiobooks\"]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div/div[2]/div/div[2]/section[2]/ol/li[1]/div/div[2]/div[5]/div[2]/div/div/form/input[11]")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        
        action.sendKeys(Keys.HOME).perform();
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        String count=driver.findElement(By.xpath("/html/body/div[2]/header/nav/div/div[3]/ul/li[1]/a/span[2]")).getText();
        if (!count.equals("0")) {
            test2.log(Status.PASS, "Item added to the cart");
        }
        else
        {
            test2.log(Status.FAIL, "Item Not added to the cart");
        }
    }
    @Test(priority = 2)
    public void testCase3() throws InterruptedException, IOException {
        ExtentTest test3=reports.createTest("Test 3");
        driver.get(url);
        // Wait for the cookie consent button to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));
        acceptButton.click();

        // Click on the desired element after the cookie consent is accepted
        driver.findElement(By.xpath("/html/body/div[4]/div/dd/div/div/div[1]/div/a[5]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"rewards-modal-link\"]")).click();
        Thread.sleep(2000);

        // Take a screenshot
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = "C:\\Users\\karth.KARTHIKEYAN\\OneDrive\\Desktop\\Software Testing\\cc2\\src\\screenshot.png";
        FileUtils.copyFile(screenshotFile, new File(screenshotPath));
        File screenshot = new File(screenshotPath);
        if (screenshot.exists()) {
            test3.log(Status.PASS, "Screenshot Taken");
        } else {
            test3.log(Status.FAIL, "Failed to take screenshot");
        }
    }

    @AfterTest
    public void AfterTest(){
        reports.flush();
        driver.quit();
    }
}
