package com.kayak.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.testng.annotations.DataProvider;

public class TestDataManager {
	
	public static HSSFWorkbook WorkBook;
	public static HSSFSheet WorkSheet;
    public static HSSFCell Cell;
    public static HSSFRow Row;
    public static DataFormatter formatter= new DataFormatter();
    public static String file_location = System.getProperty("user.dir")+"\\resources\\testdata\\Kayak_FlightSearchDetails.xls";
    static String SheetName= "Kayak_Flights&AirportsDetails";
	
	/*@DataProvider(name="KayakFlightsAndAirportsSearchData")
	Object[][] trainerInfoFromExcel()throws Exception{
		String sFile = System.getProperty("user.dir")+"\\resources\\testdata\\Kayak_FlightSearchDetails.xls";
		 return getExcelData(sFile);
	}*/
    @DataProvider(name="KayakFlightsAndAirportsSearchData")
	public static Object[][] getExcelData(String sFile) throws Exception {
		File fi = new File(System.getProperty("user.dir")+"\\resources\\testdata\\Kayak_FlightSearchDetails.xls");
		FileInputStream fstream = new FileInputStream(fi);
		HSSFWorkbook myExcelBook = new HSSFWorkbook(fstream);
		HSSFSheet myExcleSheet = myExcelBook.getSheet("Kayak_Flights&AirportsDetails");
		HSSFRow row1 = myExcleSheet.getRow(0);
		System.out.println(row1.getPhysicalNumberOfCells()); //To take total number of columns
		System.out.println(myExcleSheet.getPhysicalNumberOfRows());//To take total number of Rows
		//System.out.println(row1.getLastCellNum());
		int iCountCol =row1.getLastCellNum();
		int iCountRow = myExcleSheet.getPhysicalNumberOfRows();
		Object[][] excelData= new Object[iCountRow][iCountCol]; //Creating multi dimensional array
		
		for(int countRow=0;countRow<iCountRow;countRow++) {
			for(int countCol = 0; countCol<iCountCol;countCol++) {
				HSSFRow tempRow=myExcleSheet.getRow(countRow);
				String sTemp;
				try {
				System.out.println(tempRow.getCell(countCol).getStringCellValue());
				sTemp=tempRow.getCell(countCol).getStringCellValue();
				}catch(Exception e) {
					System.out.println(tempRow.getCell(countCol).getNumericCellValue());
					sTemp=Double.toString(tempRow.getCell(countCol).getNumericCellValue());
				}
				excelData[countRow][countCol] = sTemp;
			}
		}
//		myExcelBook.close();
		return excelData;
	}
	
    @DataProvider(name="KayakFlightsAndAirportsSearchData")
	public static Object[][] readExcelData(String sFile) throws IOException
    {
    FileInputStream fileInputStream= new FileInputStream(file_location); //Excel sheet file location get mentioned here
    WorkBook = new HSSFWorkbook (fileInputStream); //get my workbook 
    WorkSheet=WorkBook.getSheet(SheetName);// get my sheet from workbook
    Row=WorkSheet.getRow(0);     //get my Row which start from 0   
    int RowNum = WorkSheet.getPhysicalNumberOfRows();// count my number of Rows
    int ColNum= Row.getLastCellNum(); // get last ColNum 
    Object Data[][]= new Object[RowNum-1][ColNum]; // pass my  count data in array
         
            for(int i=0; i<RowNum-1; i++) //Loop work for Rows
            {  
                Row= WorkSheet.getRow(i+1);
                 
                for (int j=0; j<ColNum; j++) //Loop work for colNum
                {
                    if(Row==null)
                        Data[i][j]= "";
                    else
                    {
                        HSSFCell Cell= Row.getCell(j);
                        if(Cell==null)
                            Data[i][j]= ""; //if it get Null value it pass no data 
                        else
                        {
                            String value=formatter.formatCellValue(Cell);
                            Data[i][j]=value; //This formatter get my all values as string i.e integer, float all type data value
                            System.out.println(Data[i][j]);
                        }
                    }
                }
            }
 
        return Data;
    }
}
	



