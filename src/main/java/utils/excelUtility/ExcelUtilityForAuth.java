package utils.excelUtility;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtilityForAuth {
    
    public static String getDataFromExcel(String SheetName,int rowNum, int cellNum) throws Throwable {
	FileInputStream fis=new FileInputStream("src/test/resources/testdata/AuthData.xlsx");
	Workbook wb=WorkbookFactory.create(fis);
	String data=wb.getSheet(SheetName).getRow(rowNum).getCell(cellNum).getStringCellValue();
	 wb.close();
     return data;
	}

}
