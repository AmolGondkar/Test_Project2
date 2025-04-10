package Test1;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;

public class OpenTabsOfficialWay {

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Step 1: Open Admin page in default tab
        driver.get("https://staging.investorstatelawguide.com/Admin/");
        System.out.println("Opened Admin in Tab 1");

        // Step 2: Open a new tab (Selenium 4 method)
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://staging.investorstatelawguide.com/Login/");
        System.out.println("Opened Login in Tab 2");

        // Step 3: Get handles and switch back to Admin tab if needed
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
        System.out.println("Back to Admin tab");

        Thread.sleep(3000);
        driver.quit();
    }
}
