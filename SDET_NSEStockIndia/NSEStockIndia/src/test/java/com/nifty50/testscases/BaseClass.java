package com.nifty50.testscases;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import Utilities.Log;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	public WebDriver driver;
	public Logger log = LogManager.getLogger(BaseClass.class);
	@BeforeMethod
	@Parameters({"Browser","URL"})
	public void Setup(String Browser,String url) throws InterruptedException {

		Log.info("Setting up Browser ");
		if(Browser.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver(options);	
		}
		else if(Browser.equalsIgnoreCase("edge")) {
			EdgeOptions options = new EdgeOptions();
			options.addArguments("--remote-allow-origins=*");
			WebDriverManager.edgedriver().setup();
			driver=new EdgeDriver(options);
		}
		else if(Browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
		}
		
		driver.get(url);
		driver.manage().window().maximize();
        Thread.sleep(3000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
	}
	@AfterMethod
	public void tearDown() {
		Log.info("Browser session closed");
		driver.quit();
	}
	
	public String captureScreenshot(WebDriver driver,String tname,String status)throws IOException {
		String datestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		TakesScreenshot screenShot =(TakesScreenshot)driver;
		File source=screenShot.getScreenshotAs(OutputType.FILE);
		String  path = System.getProperty("user.dir")+"//screenshot//"+"//"+status+"//"+tname+datestamp+".png";
		File target = new File(path);
		FileUtils.copyFile(source, target);
		Log.info("Screenshot captured");
		return  path;
	}
}
