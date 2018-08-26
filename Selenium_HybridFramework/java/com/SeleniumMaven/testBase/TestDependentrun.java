package com.SeleniumMaven.testBase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestDependentrun {

	
	

	
	
	@Test(dependsOnMethods ="Method3")
	public void Method1()
	{
		System.out.println("Run method1");
	}
	@Test()
	public void Method2()
	{
		System.out.println("Run method2");
	}
	@Test()
	public void Method3()
	{
		System.out.println("Run method3");
		Assert.assertTrue(false);
	}
	
}
