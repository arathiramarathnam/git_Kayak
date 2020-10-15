/*package com.kayak.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DataProvider {
	
    private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;
    private static XSSFCell Cell;
    private static XSSFRow Row;
	
	   public static Object[][] getExcelData(String FilePath, String SheetName) throws Exception {
	        String[][] tabArray = null;

	        try {
	            // Access the required test data sheet
	            FileInputStream ExcelFile = new FileInputStream(FilePath);
	            ExcelWBook = new XSSFWorkbook(ExcelFile);
	            ExcelWSheet = ExcelWBook.getSheet(SheetName);

	            int totalNoOfCols = 10;
	            int totalNoOfRows = ExcelWSheet.getLastRowNum();

	            tabArray = new String[totalNoOfRows][totalNoOfCols];

	            for (int i = 1; i <= totalNoOfRows; i++) {
	                for (int j = 0; j < totalNoOfCols; j++) {
	                    Cell = ExcelWSheet.getRow(i).getCell(j);
	                    int cel_Type = Cell.getCellType();
	                    switch (cel_Type) {
	                    case XSSFCell.CELL_TYPE_NUMERIC: // 0
	                        if (DateUtil.isCellDateFormatted(Cell)) {
	                            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
	                            tabArray[i - 1][j] = df.format(Cell.getDateCellValue());
	                        } else {
	                            tabArray[i - 1][j] = String.format("%d", (long) Cell.getNumericCellValue());
	                        }
	                        break;
	                    case XSSFCell.CELL_TYPE_STRING: // 1
	                        tabArray[i - 1][j] = Cell.getStringCellValue();
	                        break;
	                    }
	                }
	            }
	        } catch (FileNotFoundException e) {
	            System.out.println("Could not read the Excel sheet");
	            e.printStackTrace();
	        } catch (IOException e) {
	            System.out.println("Could not read the Excel sheet");
	            e.printStackTrace();
	        }
	        return tabArray;
	    }
	   
	    public static Object[][] getTableArray(String FilePath, String SheetName) throws Exception {   

			   String[][] tabArray = null;

			   try {

				   FileInputStream ExcelFile = new FileInputStream(FilePath);

				   // Access the required test data sheet

				   ExcelWBook = new XSSFWorkbook(ExcelFile);

				   ExcelWSheet = ExcelWBook.getSheet(SheetName);

				   int startRow = 1;

				   int startCol = 1;

				   int ci,cj;

				   int totalRows = ExcelWSheet.getLastRowNum();

				   // you can write a function as well to get Column count

				   int totalCols = 2;

				   tabArray=new String[totalRows][totalCols];

				   ci=0;

				   for (int i=startRow;i<=totalRows;i++, ci++) {           	   

					  cj=0;

					   for (int j=startCol;j<=totalCols;j++, cj++){

						   tabArray[ci][cj]=getCellData(i,j);

						   System.out.println(tabArray[ci][cj]);  

							}

						}

					}

				catch (FileNotFoundException e){

					System.out.println("Could not read the Excel sheet");

					e.printStackTrace();

					}

				catch (IOException e){

					System.out.println("Could not read the Excel sheet");

					e.printStackTrace();

					}

				return(tabArray);

				}

			public static String getCellData(int RowNum, int ColNum) throws Exception {

				try{

					Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);

					int dataType = Cell.getCellType();

					if  (dataType == 3) {

						return "";

					}else{

						String CellData = Cell.getStringCellValue();

						return CellData;

					}catch (Exception e){

					System.out.println(e.getMessage());

					throw (e);

					}

				}

	}

}
*/