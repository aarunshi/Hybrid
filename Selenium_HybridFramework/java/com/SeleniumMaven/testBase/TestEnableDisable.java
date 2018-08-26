package com.SeleniumMaven.testBase;

import org.testng.annotations.Test;

public class TestEnableDisable {

	@Test(enabled=true)
	public void method()
	{
		System.out.println("enabled method");
	}
	@Test(enabled=false)
	public void method5()
	{
		System.out.println("disnabled method");
	}

}
