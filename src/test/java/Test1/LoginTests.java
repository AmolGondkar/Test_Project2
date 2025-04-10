package Test1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginTests {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver",
                "D:\\Automation\\chromedriver134\\chromedriver\\chromedriver134.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://staging.investorstatelawguide.com/Login");
        waitForPageToLoad();
    }

    @BeforeMethod
    public void navigateToLoginPage() {
        driver.get("https://staging.investorstatelawguide.com/Login");
        System.out.println("\n==> Navigated to Login Page");
        waitForPageToLoad();
    }

    @DataProvider(name = "loginData")
    public Object[][] getData() {
        return new Object[][]{
                {"chris", "wrongpass", "Invalid user name or password"},   // TC_001
                {"invaliduser", "Admin@123", "Invalid user name or password"}, // TC_002
                {"invaliduser", "wrongpass", "Invalid user name or password"}, // TC_003
                {"' OR 1=1; --", "' OR 1=1; --", "Invalid user name or password"}, // TC_004
                {"chris", "Admin@123", ""} // TC_005 - Successful login
        };
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password, String expectedMessage) {
        System.out.println("\n===== Running Login Test Case =====");
        System.out.println("Username: " + username);
        System.out.println("Expected Message: " + expectedMessage);

        // Enter username
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("UserName")));
        usernameField.clear();
        usernameField.sendKeys(username);
        System.out.println("==> Entered Username");

        // Enter password
        WebElement passwordField = driver.findElement(By.id("Password"));
        passwordField.clear();
        passwordField.sendKeys(password);
        System.out.println("==> Entered Password");

        // Click login
        WebElement loginButton = driver.findElement(By.id("loginDetail"));
        loginButton.click();
        System.out.println("==> Clicked Login Button");

        // Check for result
        if (!expectedMessage.isEmpty()) {
            WebElement messageElement = wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//*[contains(text(),'" + expectedMessage + "')]")));
            String actualMessage = messageElement.getText();
            System.out.println("==> Actual Message: " + actualMessage);
            Assert.assertTrue(actualMessage.contains(expectedMessage), "Expected message mismatch!");
        } else {
            boolean loginSuccessful = wait.until(ExpectedConditions.urlContains("Dashboard"));
            Assert.assertTrue(loginSuccessful, "Login failed or redirect missing!");
            System.out.println("==> Login successful, redirected to Dashboard.");
        }

        System.out.println("==> Test Passed\n");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("==> Browser Closed");
        }
    }

    private void waitForPageToLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("UserName")));
        System.out.println("==> Login Page Fully Loaded");
    }
}
