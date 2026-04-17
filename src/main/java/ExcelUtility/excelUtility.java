package ExcelUtility;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class excelUtility {
    public String getDataFromExcel(String sheetName, int rowNum, int cellNum) throws Exception {
        FileInputStream fis = new FileInputStream("./testdata/updateBookingData.xlsx");
        Workbook wb = WorkbookFactory.create(fis);

        Cell cell = wb.getSheet(sheetName).getRow(rowNum).getCell(cellNum);

        String data = "";
        if (cell.getCellType() == CellType.STRING) {
            data = cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            data = String.valueOf(cell.getNumericCellValue());
        }

        wb.close();
        return data;
    }
}