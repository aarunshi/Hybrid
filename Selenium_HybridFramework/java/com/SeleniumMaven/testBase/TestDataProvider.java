package com.SeleniumMaven.testBase;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestDataProvider {
	@DataProvider(name="data")
	public Object[][] dataProviderTest()
	{
		Object[][] obj=new Object[3][3];
		
		obj[0][0]="username1";
		obj[0][1]="password1";
		obj[0][2]="1";
		obj[1][0]="username2";
		obj[1][1]="password2";
		obj[1][2]="2";
		obj[2][0]="username3";
		obj[2][1]="password3";
		obj[2][2]="3";
		return obj;
										
		
	}
	
	@Test(dataProvider="data")
	public void test(String a,String b,String c)
	{
		System.out.println(a+"::"+b+"::"+c);
	}

}
