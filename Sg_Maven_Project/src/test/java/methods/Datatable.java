package methods;

import java.io.FileInputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import driver.DriverScript;

public class Datatable extends DriverScript{
	/*******************************************
	 * method Name		: getExcelData()
	 * 
	 * 
	 *******************************************/
	public Map<String, String> getExcelData(String moduleName, String sheetName, String logicalName){
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row1 = null;
		Row row2 = null;
		Cell cell1 = null;
		Cell cell2 = null;
		Map<String, String> data = null;
		int rowNum = 0;
		int colNum = 0;
		String key = null;
		String value = null;
		Calendar cal = null;
		String day = null;
		String month = null;
		String year = null;
		try {
			data = new HashMap<String, String>();
			fin = new FileInputStream(System.getProperty("user.dir") + "\\TestData\\"+moduleName+".xlsx");
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			
			if(sh==null) {
				reports.writeReport(null, "Fail", "Failed to find the sheet '"+sheetName+"'. Hence exiting the code");
				return null;
			}
			
			//verify the logical name is present
			int rows = sh.getPhysicalNumberOfRows();
			for(int r=0; r<rows; r++) {
				row1 = sh.getRow(r);
				cell1 = row1.getCell(0);
				if(cell1.getStringCellValue().equalsIgnoreCase(logicalName)) {
					rowNum = r;
					break;
				}
			}
			
			if(rowNum > 0) {
				row1 = sh.getRow(0);
				row2 = sh.getRow(rowNum);
				colNum = row1.getPhysicalNumberOfCells();
				
				for(int c=0; c<colNum; c++) {
					cell1 = row1.getCell(c);
					cell2 = row2.getCell(c);
					key = cell1.getStringCellValue();
					
					if(cell2==null || cell2.getCellType()==CellType.BLANK) {
						value = "";
					}
					else if(cell2.getCellType()==CellType.BOOLEAN) {
						value = String.valueOf(cell2.getBooleanCellValue());
					}
					else if(cell2.getCellType()==CellType.STRING) {
						value = cell2.getStringCellValue();
					}
					else if(cell2.getCellType()==CellType.NUMERIC) {
						if(DateUtil.isCellDateFormatted(cell2)) {
							double dt = cell2.getNumericCellValue();
							cal = Calendar.getInstance();
							cal.setTime(DateUtil.getJavaDate(dt));
							
							//If date is <10, prefix with zero
							if(cal.get(Calendar.DAY_OF_MONTH) < 10) {
								day = "0" + cal.get(Calendar.DAY_OF_MONTH);
							}else {
								day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
							}
							
							//If month is <10, prefix with zero
							if((cal.get(Calendar.MONTH)+1) < 10) {
								month = "0" + (cal.get(Calendar.MONTH)+1);
							}else {
								month = String.valueOf((cal.get(Calendar.MONTH)+1));
							}
							
							year = String.valueOf(cal.get(Calendar.YEAR));
							
							value = day +"/"+ month +"/"+ year;
						}else {
							value = String.valueOf(cell2.getNumericCellValue());
						}
					}
					data.put(key, value);
				}
			}else {
				reports.writeReport(null, "Fail", "Failed to find the logical name '"+logicalName+"'. Hence exiting the code");
				return null;
			}
			return data;
		}catch(Exception e) {
			reports.writeReport(null, "Exception", "Exception in the 'getExcelData()' method. "+ e);
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cell1 = null;
				cell2 = null;
				row1 = null;
				row2 = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e) {
				reports.writeReport(null, "Exception", "Exception in the 'getExcelData()' method. "+ e);
				return null;
			}
		}
	}
	
	
	
	/*******************************************
	 * method Name		: getRowNumber()
	 * 
	 * 
	 *******************************************/
	public int getRowNumber(String filePath, String sheetName) {
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		int rowNum = 0;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			
			if(sh==null) {
				reports.writeReport(null, "Fail", "The sheet '"+sheetName+"' doesnot exist");
				return -1;
			}
			
			rowNum = sh.getPhysicalNumberOfRows()-1;
			return rowNum;
		}catch(Exception e) {
			reports.writeReport(null, "Exception", "Exception in the 'getRowNumber()' method. "+ e);
			return -1;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e) {
				reports.writeReport(null, "Exception", "Exception in the 'getRowNumber()' method. "+ e);
				return -1;
			}
		}
	}
	
	
	
	/*******************************************
	 * method Name		: getCellData()
	 * 
	 * 
	 *******************************************/
	public String getCellData(String filePath, String sheetName, String columnName, int rowNum) {
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		String strData = null;
		int colNum = 0;
		String day = null;
		String month = null;
		String year = null;
		Calendar cal = null;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			if(sh==null) {
				reports.writeReport(null, "Fail", "The sheet '"+sheetName+"' doesnot exist");
				return null;
			}
			
			//find the column number using column Name
			row = sh.getRow(0);
			int colums = row.getPhysicalNumberOfCells();
			for(int c=0; c<colums; c++) {
				cell = row.getCell(c);
				if(cell.getStringCellValue().equalsIgnoreCase(columnName)) {
					colNum = c;
					break;
				}
			}
			
			row = sh.getRow(rowNum);
			cell = row.getCell(colNum);
			
			if(cell==null || cell.getCellType()==CellType.BLANK) {
				strData = "";
			}else if(cell.getCellType()==CellType.BOOLEAN) {
				strData = String.valueOf(cell.getBooleanCellValue());
			}else if(cell.getCellType()==CellType.STRING) {
				strData = cell.getStringCellValue();
			}else if(cell.getCellType()==CellType.NUMERIC) {
				if(DateUtil.isCellDateFormatted(cell)) {
					double dt = cell.getNumericCellValue();
					cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(dt));
					
					//If day is less than 10, then prefix with zero
					if(cal.get(Calendar.DAY_OF_MONTH) < 10) {
						day = "0" + cal.get(Calendar.DAY_OF_MONTH);
					}else {
						day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
					}
					
					//If month is less than 10, then prefix with zero
					if((cal.get(Calendar.MONTH)+1) < 10) {
						month = "0" + (cal.get(Calendar.MONTH)+1);
					}else {
						month = String.valueOf((cal.get(Calendar.MONTH)+1));
					}
					
					year = String.valueOf(cal.get(Calendar.YEAR));
					
					strData = day+"/"+month+"/"+year;
				}else {
					strData = String.valueOf(cell.getNumericCellValue());
				}
			}
			return strData;
		}catch(Exception e) {
			reports.writeReport(null, "Exception", "Exception in the 'getCellData()' method. "+ e);
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e) {
				reports.writeReport(null, "Exception", "Exception in the 'getCellData()' method. "+ e);
				return null;
			}
		}
	}
}
