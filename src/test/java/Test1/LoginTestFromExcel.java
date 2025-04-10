package Test1;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;

public class LoginTestFromExcel {

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
        System.out.println("==> Navigated to Login Page");
        waitForPageToLoad();
    }

    @DataProvider(name = "loginData")
    public Object[][] getData() throws IOException {
        return readExcelData("D:\\Automation\\TestData.xlsx", "Sheet1");
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password, String expectedMessage) {
        System.out.println("=== [Excel Test] Running with Username: " + username + ", Expected: " + expectedMessage);

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("UserName")));
        usernameField.clear();
        usernameField.sendKeys(username);

        WebElement passwordField = driver.findElement(By.id("Password"));
        passwordField.clear();
        passwordField.sendKeys(password);

        driver.findElement(By.id("loginDetail")).click();

        if (!expectedMessage.isEmpty()) {
            WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'" + expectedMessage + "')]")));
            Assert.assertTrue(messageElement.getText().contains(expectedMessage), "Expected message mismatch!");
        } else {
            boolean loginSuccessful = wait.until(ExpectedConditions.urlContains("Dashboard"));
            Assert.assertTrue(loginSuccessful, "Login failed, user not redirected!");
        }
        System.out.println("==> Test Passed\n");
    }

    // Optional teardown if needed after all tests
    // @AfterSuite
    // public void tearDownSuite() {
    //     if (driver != null) {
    //         driver.quit();
    //         System.out.println("==> Browser Closed After Suite");
    //     }
    // }

    public Object[][] readExcelData(String filePath, String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
        Object[][] data = new Object[rowCount - 1][colCount];

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next(); // skip header

        int i = 0;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            for (int j = 0; j < colCount; j++) {
                data[i][j] = row.getCell(j).toString();
            }
            i++;
        }

        workbook.close();
        fis.close();
        return data;
    }

    private void waitForPageToLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("UserName")));
        System.out.println("==> Login Page Fully Loaded");
    }
}
