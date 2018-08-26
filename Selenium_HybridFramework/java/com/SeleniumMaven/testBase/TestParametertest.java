package com.SeleniumMaven.testBase;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestParametertest {
	
	@Test()
	@Parameters({"data1","data2"})
	public void Method1(@Optional("data1Optional")String data1,@Optional("data1Optional")String data2)
	{
		System.out.println("Run method1");
		System.out.println(data1);
		System.out.println(data2);
		System.out.println("Reading jenkin Parameter");
		System.out.println("Name is"+ System.getProperty("Name"));
		System.out.println("Env is"+ System.getProperty("env"));
	}

}
