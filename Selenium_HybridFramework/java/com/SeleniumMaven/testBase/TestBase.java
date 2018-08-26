package com.SeleniumMaven.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.poifs.property.DirectoryProperty.PropertyComparator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.SeleniumMaven.excelReader.ExcelReader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class TestBase {
	public Properties OR;
	public WebDriver driver;
	public FileInputStream file;
	public File f1;
	public static ExtentReports extent;
	public static ExtentTest test;
	public ITestResult result;
	public static final Logger logger = Logger.getLogger(TestBase.class.getName());
	
	
	static 
	{
		Calendar calender = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_mm_yyyy_hh_mm_ss");
		extent = new ExtentReports(System.getProperty("user.dir")+"/src/main/java/com/SeleniumMaven/Reports/test"+formater.format(calender.getTime())+".html",false);
		System.out.println(System.getProperty("user.dir")+"/src/main/java/com/SeleniumMaven/Reports/test"+formater.format(calender.getTime())+".html");
	}
			
	public void getBrowser(String browser)
	{
		
		if(System.getProperty("os.name").contains("Window"))//to support different OS
		{
			if(browser.equalsIgnoreCase("FireFox"))
			{
				System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"/drivers/geckodriver.exe");
				driver = new FirefoxDriver();
			
			}
			if(browser.equalsIgnoreCase("chrome"))
			{
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/drivers/chromedriver.exe");
				driver = new ChromeDriver();
			System.out.println("driver created"+System.getProperty("os.name"));
			}
		}
		
		
	}
	public void loadPropertiesFile() throws IOException
	{
		 OR = new Properties();
		 f1 = new File(System.getProperty("user.dir")+"/src/main/java/com/SeleniumMaven/config/or.properties");
		 file = new FileInputStream(f1);
		 OR.load(file);
		 
		 
		 f1 = new File(System.getProperty("user.dir")+"/src/main/java/com/SeleniumMaven/config/config.properties");
		 file = new FileInputStream(f1);
		 OR.load(file);
		 
		 f1 = new File(System.getProperty("user.dir")+"/src/main/java/com/SeleniumMaven/Properties/HomePage.properties");
		 file = new FileInputStream(f1);
		 OR.load(file);
	}
	
	public String getScreenShot(String imageName) throws IOException
	{
		
		if (imageName.equals(""))
		{
			imageName ="Blank";
		}
		File image =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String imageLocation =System.getProperty("user.dir")+"src/main/java/com/SeleniumMaven/screenshots/";
		Calendar calender = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_mm_yyyy_hh_mm_ss");
		String actualImageName =imageLocation+imageName+"_"+formater.format(calender.getTime())+".png";
		File desFile = new File (actualImageName);
		FileHandler.copy(image, desFile);
		return actualImageName;
		
	}
	
	public WebElement waitForElement(WebDriver driver,long time,WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver,time);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	public WebElement waitForElementWithPolingInterval(WebDriver driver,long time,WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver,time);
		wait.pollingEvery(5, TimeUnit.SECONDS);
		wait.ignoring(NoSuchElementException.class);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	public void implicitWait( long time)
	{
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	
	}
	
	
	
	public void getResults(ITestResult result) throws IOException
	{
		if (result.getStatus()==ITestResult.SUCCESS)
		{
			test.log(LogStatus.PASS, result.getName()+"test is pass");
		}
		 if (result.getStatus()==ITestResult.FAILURE)
		 {
			 test.log(LogStatus.FAIL, result.getName()+"test is failed");
			String screen =  getScreenShot(" ");
			test.log(LogStatus.FAIL,test.addScreenCapture(screen));
		 }
		 if (result.getStatus()==ITestResult.SKIP)
		 {
			 test.log(LogStatus.SKIP, result.getName()+"test is skipped");
		 }
		 if (result.getStatus()==ITestResult.STARTED)
		 {
			 test.log(LogStatus.INFO, result.getName()+"test is started");
		 }
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException
	{
		getResults(result);
		System.out.println("in after method");
	}
	@BeforeMethod
	public void beforeMethod(ITestResult result)
	{
		test = extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName()+"TestStarted");
		System.out.println("in before method");
	}
	@AfterClass()
	public void endTest()
	{
		//driver.quit();
		extent.endTest(test);
		extent.flush();
		System.out.println("in after class");
	}
	public WebElement getLocator(String locator) throws Exception
	{
		 String[] arr = locator.split(":");
		 String locatorType = arr[0];
		 String locatorValue = arr[1];
		 System.out.println(locatorType+"::"+locatorValue);
		 if (locatorType.toLowerCase().equals("id"))
		 {
			 return driver.findElement(By.id(locatorValue));
		 }
		 else if (locatorType.toLowerCase().equals("xpath"))
		 {
			 return driver.findElement(By.xpath(locatorValue));
		 }
		 
		 else if (locatorType.toLowerCase().equals("name"))
		 {
			 return driver.findElement(By.name(locatorValue));
		 }
		 else if ((locatorType.toLowerCase().equals("classname"))||(locatorType.toLowerCase().equals("class")))
		 {
			 return driver.findElement(By.className(locatorValue));
		 }
		 else if (locatorType.toLowerCase().equals("tag")||(locatorType.toLowerCase().equals("tagname")))
		 {
			 return driver.findElement(By.tagName(locatorValue));
		 }
		 else if (locatorType.toLowerCase().equals("link")||(locatorType.toLowerCase().equals("linktext")))
		 {
			 return driver.findElement(By.linkText(locatorValue));
		 }
		 else if (locatorType.toLowerCase().equals("partiallinktext"))
		 {
			 return driver.findElement(By.partialLinkText(locatorValue));
		 }
		 else if (locatorType.toLowerCase().equals("cssselector")||(locatorType.toLowerCase().equals("css")))
		 {
			 return driver.findElement(By.cssSelector(locatorValue));
		 }
		 else
			 throw new Exception("Unknown Locator Type"+locatorType);
		 
		 		 
		 
	}
	public List<WebElement> getLocators(String locator) throws Exception
	{

		 String[] arr = locator.split(":");
		 String locatorType = arr[0];
		 String locatorValue = arr[1];
		 if (locatorType.toLowerCase().equals("id"))
		 {
			 return driver.findElements(By.id(locatorValue));
		 }
		 else if (locatorType.toLowerCase().equals("xpath"))
		 {
			 return driver.findElements(By.xpath(locatorValue));
		 }
		 
		 else if (locatorType.toLowerCase().equals("name"))
		 {
			 return driver.findElements(By.name(locatorValue));
		 }
		 else if ((locatorType.toLowerCase().equals("classname"))||(locatorType.toLowerCase().equals("class")))
		 {
			 return driver.findElements(By.className(locatorValue));
		 }
		 else if (locatorType.toLowerCase().equals("tag")||(locatorType.toLowerCase().equals("tagname")))
		 {
			 return driver.findElements(By.tagName(locatorValue));
		 }
		 else if (locatorType.toLowerCase().equals("link")||(locatorType.toLowerCase().equals("linktext")))
		 {
			 return driver.findElements(By.linkText(locatorValue));
		 }
		 else if (locatorType.toLowerCase().equals("partiallinktext"))
		 {
			 return driver.findElements(By.partialLinkText(locatorValue));
		 }
		 else if (locatorType.toLowerCase().equals("cssselector")||(locatorType.toLowerCase().equals("css")))
		 {
			 return driver.findElements(By.cssSelector(locatorValue));
		 }
		 else
			 throw new Exception("Unknown Locator Type"+locatorType);
	}
	
	public WebElement getWebElement(String locator) throws Exception
	{
		return getLocator(locator);
	}
	public List<WebElement> getWebElements(String locator) throws Exception
	{
		return getLocators(locator);
	}
	public void getPropertiesData()
	{
		
	}
	@Test()
	public void main() throws Exception
	{
		TestBase test = new TestBase();
		
		
		String log4jConfPath=System.getProperty("user.dir")+ "/Log4J.properties";
		PropertyConfigurator.configure(log4jConfPath);
		logger.info("log4jmessage");
		
		logger.info("log4jmessage");
		logger.debug("debug");
		logger.info("log4jmessage");
		//test.getBrowser("chrome");
		//test.loadPropertiesFile();
		//test.getWebElement(test.OR.getProperty("username"));
		System.out.println("in main");
		ExcelReader ER = new ExcelReader();
		ER.getExcelData(System.getProperty("user.dir")+"/src/main/java/com/SeleniumMaven/data/Book1.xlsx", "Sheet1");
		
	}
	@Test()
	public void appu()
	{
		System.out.println("first annotaion");
	}
}
