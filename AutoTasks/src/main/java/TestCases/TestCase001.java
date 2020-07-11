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

public class TestCase001 {

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
		
		driver.get("http://127.0.0.1:5500/index.html");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void checkForm() throws InterruptedException {
		
		for(int t= 0;t<3;t++) {
		
		WebElement fullName= driver.findElement(By.id("fullName"));
		fullName.sendKeys("employeeOne");
		
		WebElement empCode= driver.findElement(By.id("empCode"));
		empCode.sendKeys("1001");
		
		WebElement salary= driver.findElement(By.id("salary"));
		salary.sendKeys("50000");
		
		WebElement city= driver.findElement(By.id("city"));
		city.sendKeys("chennai");
		
		WebElement date= driver.findElement(By.id("date"));
		date.sendKeys("04072020");
		
		
		List<WebElement> select= driver.findElements(By.id("select"));
		Actions action = new Actions(driver);
		action.keyDown(Keys.CONTROL)
		.click(select.get(0))
		.build().perform();
		
		WebElement color= driver.findElement(By.id("color"));
		String myColor=color.getCssValue("color");
		System.out.println(myColor);
		
		WebElement submit= driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div[10]/input"));
		submit.click();
		
		Thread.sleep(3000);
		List<WebElement> output=driver.findElements(By.xpath("//*[@id='employeeList']/tbody"));
		List<String> allVal = new ArrayList<String>();
		for (WebElement val : output) {
			allVal.add(val.getText());
		}
		System.out.println(allVal);
		
					
	}
	
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
