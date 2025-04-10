package Test1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class Screenshot_Test1 {

    public static void main(String[] args) throws Exception {

        System.setProperty("webdriver.chrome.driver", "D:\\Automation\\chromedriver134\\chromedriver\\chromedriver134.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://staging.investorstatelawguide.com/Admin/");
        
        System.out.println(driver.getCurrentUrl());
        System.out.println(driver.getTitle());

        // Perform login
        driver.findElement(By.xpath("//input[@id='UserName']")).sendKeys("admin");  // Username
        driver.findElement(By.xpath("//input[@id='Password']")).sendKeys("Admin@123"); // Password
        driver.findElement(By.xpath("//button[@id='btnLogin']")).click(); // Login Button Click
        
        
        Thread.sleep(2000);

        // Take a screenshot after successful login
        takeScreenshot(driver, "D:\\Automation\\Screenshots\\LoginSuccess.png");

        driver.quit();
    }

    // Method to capture and save screenshot
    public static void takeScreenshot(WebDriver driver, String filePath) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File(filePath));
            System.out.println("Screenshot saved at: " + filePath);
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}
