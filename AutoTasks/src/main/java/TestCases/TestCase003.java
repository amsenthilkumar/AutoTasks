package TestCases;


import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class TestCase003 {

	WebDriver driver = null;
	@BeforeSuite
	public void beforeTest() throws IOException {
		
		
		FileInputStream stream = new FileInputStream("config.properties");
		Properties properties = new Properties();
		properties.load(stream);
		String browser = properties.getProperty("browser");
		String DriverLocation = properties.getProperty("DriverLocation");
		String name= properties.getProperty("name");
		
		if(browser.equalsIgnoreCase("chrome")) {
			System.setProperty(name,DriverLocation);
			driver = new ChromeDriver();
		}
		
		driver.get("http://127.0.0.1:5500/SRC_CODE/flagRest.html");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void checkForm() throws InterruptedException {
		
		
		
		WebElement myInput= driver.findElement(By.id("myInput"));
		myInput.sendKeys("india");
		
		
		
				
		Thread.sleep(2000);
//		List<WebElement> output=driver.findElements(By.xpath("//*[@id='employeeList']/tbody"));
//		List<String> allVal = new ArrayList<String>();
//		for (WebElement val : output) {
//			allVal.add(val.getText());
//		}
//		System.out.println(allVal);

		WebElement output = driver.findElement(By.tagName("h1"));
		System.out.println(output.getText());
		
		
					
	}
	
	
	
	@AfterSuite
	public void afterTest() throws AWTException, IOException {
		Robot robot = new Robot(); 
		Dimension screensize= Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle rectangle = new Rectangle(screensize);
		BufferedImage source= robot.createScreenCapture(rectangle);
		File destinationFile = new File("D:\\dump\\snap.png");
		ImageIO.write(source, "png", destinationFile);
	}
	
}

