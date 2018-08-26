package com.SeleniumMaven.testBase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestGroupExecute {
	
	@BeforeClass(groups={"sanity","Reg"})
	public void beforeClass()
	{
		System.out.println("before class setup");
	}
	@BeforeMethod(groups={"sanity","Reg"})
	public void beforeMethod()
	{
		System.out.println("before method setup");
	}
	@AfterMethod(groups={"sanity","Reg"})
	public void afterMethod()
	{
		System.out.println("After Method setup");
	}
	@Test(groups ={"sanity"})
	public void TestSanitySet()
	{
		System.out.println("Run sanity suite");//
	}
	@Test(groups ={"Reg"})
	public void TestRegSet()
	{
		System.out.println("Run Reg suite");
	}
	@Test(groups={"sanity","Reg"})
	public void TestBothSet()
	{
		System.out.println("Run both suite");
	}
}
