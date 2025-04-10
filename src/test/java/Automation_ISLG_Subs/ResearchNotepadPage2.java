package Automation_ISLG_Subs;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

public class ResearchNotepadPage2 {
	public static void main(String[] args) throws InterruptedException {
		// Set up Edge WebDriver
		System.setProperty("webdriver.edge.driver", "D:\\Automation\\Drivers\\msedgedriver134.exe");
		WebDriver driver = new EdgeDriver();

		// Maximize browser window
		driver.manage().window().maximize();

		// Navigate to the target page
		driver.get("https://staging.investorstatelawguide.com/");

		// Login
		driver.findElement(By.xpath("//input[@id='UserName']")).sendKeys("chris"); // Username
		driver.findElement(By.xpath("//input[@id='Password']")).sendKeys("Admin@123"); // Password
		driver.findElement(By.xpath("//button[@id='loginDetail']")).click(); // Login Button Click

		// Navigate to Research Notepad
		driver.findElement(By.xpath("//a[@href='/ResearchNotepad']")).click();

		// Click 'Skip' button if present
		try {
			WebElement skipButton = driver.findElement(By.xpath("//button[normalize-space()='Skip']"));
			if (skipButton.isDisplayed()) {
				skipButton.click();
				System.out.println("Clicked on 'Skip'");
			}
		} catch (Exception e) {
			System.out.println("Skip button not found, proceeding...");
		}

		// Click 'Create New Topic' button
		driver.findElement(By.xpath("//button[@id='btnCreateNewTopic']")).click();
		System.out.println("Clicked on 'Create Research Topic' button");
		Thread.sleep(4000);

		// Enter research topic name
		WebElement topicNameField = driver.findElement(By.xpath("//input[@id='TopicName']"));
		String topicName = "Test Research Topic"; // Default topic name
		topicNameField.sendKeys(topicName);
		System.out.println("Entered research topic name: " + topicName);

		// Click on the description field to trigger validation
		WebElement researchTopicDescription = driver.findElement(By.xpath("//textarea[@id='TopicDescription']"));
		researchTopicDescription.click();
		System.out.println("Clicked on description field to trigger validation");

		// Wait for validation message (if any)
		Thread.sleep(2000);

		// Check if 'Topic name already exists.' message appears
		try {
			WebElement errorMessage = driver
					.findElement(By.xpath("//*[contains(text(), 'Topic name already exists.')]"));
			if (errorMessage.isDisplayed()) {
				System.out.println("Error: Topic name already exists.");

				// Enter a new unique topic name
				topicNameField.clear();
				topicName = "Test Research Topic " + System.currentTimeMillis();
				topicNameField.sendKeys(topicName);
				System.out.println("Entered a new unique topic name: " + topicName);

				// Click on the description field again to trigger validation
				researchTopicDescription.click();
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			System.out.println("No duplicate topic error message found, proceeding...");
		}

		// Enter research topic description
		researchTopicDescription.sendKeys("This is a test description.");
		System.out.println("Entered research topic description");

		// Click 'Create' button
		driver.findElement(By.xpath("//button[@id='btnCreateTopic']")).click();
		System.out.println("Clicked on 'Create' button");
		System.out.println("Topic Created: " + topicName);

		// Wait for the topic to be created
		Thread.sleep(4000);

// Search for the created topic
		WebElement searchBox = driver.findElement(By.id("Serach"));
		searchBox.sendKeys(topicName + Keys.ENTER);
		System.out.println("Searched for topic: " + topicName);
		Thread.sleep(4000);

// Verify if the search results contain the topic
		if (driver.getPageSource().contains(topicName)) {
			System.out.println("Search successful: Topic found.");
		} else {
			System.out.println("Search failed: Topic not found.");
		}
	
		Thread.sleep(2000);
		
//Edit Latest created Research Topic 
	
		driver.findElement(By.xpath("(//button[contains(@id, 'Edit-icon-')]/i[contains(@class, 'fa-pencil')])[last()]")).click();

		Thread.sleep(6000);
		WebElement topicNameInput = driver.findElement(By.xpath("//input[@id='TopicName']"));
		topicNameInput.clear();
		topicNameInput.sendKeys(topicName + "123");

		WebElement topicDescInput = driver.findElement(By.xpath("//textarea[@id='TopicDescription']"));
		topicDescInput.clear();
		topicDescInput.sendKeys(topicName + "123");
		
		driver.findElement(By.xpath("//button[@id='btnCreateTopic']")).click();
		
		Thread.sleep(2000);
		
		// Close the browser
		driver.quit();
		System.out.println("Test completed successfully.");
	}
}
