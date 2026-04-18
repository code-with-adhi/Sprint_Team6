package ExcelUtility;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class excelUtility {
    public String getDataFromExcel(String sheetName, int rowNum, int cellNum) throws Exception {
    	InputStream fis = getClass().getClassLoader()
                .getResourceAsStream("testdata/updateBookingData.xlsx");
    	Workbook wb = WorkbookFactory.create(fis);

    	Sheet sheet = wb.getSheetAt(0);

        // ✅ Safety checks (avoid NullPointerException)
        if (sheet.getRow(rowNum) == null) {
            throw new RuntimeException("Row not found: " + rowNum);
        }

        if (sheet.getRow(rowNum).getCell(cellNum) == null) {
            throw new RuntimeException("Cell not found at column: " + cellNum);
        }

        Cell cell = sheet.getRow(rowNum).getCell(cellNum);

        String data = "";

        if (cell.getCellType() == CellType.STRING) {
            data = cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            data = String.valueOf((int) cell.getNumericCellValue());
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            data = String.valueOf(cell.getBooleanCellValue());
        }

        wb.close();
        return data;
    }
}