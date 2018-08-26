package com.SeleniumMaven.excelReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelReader {
	

	public void getExcelData(String excelLocation, String sheetName) throws IOException
	{
		String dataSets[][]= null;
		File f = new File(excelLocation);
		FileInputStream file = new FileInputStream(f);
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet wsh = wb.getSheet(sheetName);
		int rowNum= wsh.getLastRowNum()+1;
		int colNum =wsh.getRow(0).getLastCellNum();//colum is called cell
		dataSets = new String[rowNum-1] [colNum];
		Iterator<Row> rawIterator = wsh.iterator();
		int i=0;
		int t=1;
		int k=0;
		Row raw = rawIterator.next();
		while(rawIterator.hasNext())
		{
			
			 raw = rawIterator.next();
			if(i++!=0)
			{
				
				k=t;
				
				t++;
				
			}
			Iterator<Cell> cellIterator = raw.cellIterator();
			int j=0;
	
		{}
		
			while(cellIterator.hasNext())
			{
				Cell cell = cellIterator.next();
				
				
				dataSets[k][j++] = cell.getStringCellValue();
				
				System.out.println(cell.getStringCellValue());
			}
		}
		
	}

}
