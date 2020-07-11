package TestCases;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class TestCase002 {

	WebDriver driver = null;
	private static HttpURLConnection connection;
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
		
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();
		try {
			
			URL url = new URL("https://jsonplaceholder.typicode.com/posts/1");
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(4000);
			connection.setReadTimeout(4000);
			
			int status = connection.getResponseCode();
			
			if(status >299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line =reader.readLine())!=null) {
					responseContent.append(line);
				}
				reader.close();
			}
			else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line =reader.readLine())!=null) {
					responseContent.append(line);
				}
				reader.close();
			}
			String myUrl =  responseContent.toString();
			System.out.println(myUrl);
		}
		catch(Exception e){
			e.printStackTrace();
		}	

		
		
		driver.get("http://127.0.0.1:5500/todo.html");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void checkTodo() throws InterruptedException {
		
		WebElement todoInput= driver.findElement(By.id("todo-input"));
		todoInput.sendKeys("dummyText");
		
		WebElement todoButton= driver.findElement(By.id("todo-button"));
		todoButton.click();
		
				
		List<WebElement> output=driver.findElements(By.xpath("//*[@id=\"todo-ul\"]/li"));
		List<String> allVal = new ArrayList<String>();
		for (WebElement val : output) {
			allVal.add(val.getText());
		}
		System.out.println(allVal);
		
					
	}
	
	
	
	@AfterSuite
	public void afterTest() throws AWTException, IOException {
		Robot robot = new Robot(); 
		Dimension screensize= Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle rectangle = new Rectangle(screensize);
		BufferedImage source= robot.createScreenCapture(rectangle);
		File destinationFile = new File("D:\\dump\\snap1.png");
		ImageIO.write(source, "png", destinationFile);
	}
	
}
