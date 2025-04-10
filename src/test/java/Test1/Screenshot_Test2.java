package Test1;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;  // Make sure to add Apache Commons IO library
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Screenshot_Test2 {

    public static void main(String[] args) throws Exception {

        // Set up WebDriver for Chrome
        System.setProperty("webdriver.chrome.driver", "D:\\Automation\\chromedriver134\\chromedriver\\chromedriver134.exe");

        WebDriver driver = new ChromeDriver();  // Launch browser
        driver.manage().window().maximize();

        // Navigate to the website
        driver.get("https://staging.investorstatelawguide.com/Admin/");
        System.out.println(driver.getCurrentUrl());
        System.out.println(driver.getTitle());

        // Perform login
        driver.findElement(By.xpath("//input[@id='UserName']")).sendKeys("admin");  // Username
        driver.findElement(By.xpath("//input[@id='Password']")).sendKeys("Admin@123");  // Password
        driver.findElement(By.xpath("//button[@id='btnLogin']")).click();  // Login Button Click

        Thread.sleep(2000); // Wait for login to complete
        
        // Take screenshot after successful login
        takeScreenshot(driver, "LoginSuccess.png");

        // Close the browser
        driver.quit();
    }

    // Method to capture and save a screenshot inside the project
    public static void takeScreenshot(WebDriver driver, String fileName) {
        // Define the relative path inside the project directory
        String filePath = System.getProperty("user.dir") + "/Screenshots/" + fileName;
        
        // Ensure the "Screenshots" folder exists
        File screenshotsDir = new File(System.getProperty("user.dir") + "/Screenshots");
        
        if (!screenshotsDir.exists()) 
        {
            screenshotsDir.mkdir();  // Create the folder if it doesn't exist
        }

        // Take screenshot
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        
        try {
            File destFile = new File(filePath);
            FileUtils.copyFile(srcFile, destFile);
            System.out.println("Screenshot saved at: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
   
    
}
