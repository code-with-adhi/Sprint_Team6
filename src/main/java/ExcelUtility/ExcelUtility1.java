package ExcelUtility;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.*;

public class ExcelUtility1 {

    private Workbook wb;

    public void ExcelUtility(String fileName) throws Exception {
        InputStream fis = getClass().getClassLoader()
                .getResourceAsStream("testdata/" + fileName);
        wb = WorkbookFactory.create(fis);
    }

    public String getDataFromExcel(String sheetName, int rowNum, int cellNum) {

        Sheet sheet = wb.getSheet(sheetName);

        if (sheet == null || sheet.getRow(rowNum) == null ||
            sheet.getRow(rowNum).getCell(cellNum) == null) {
            throw new RuntimeException("Invalid cell reference");
        }

        Cell cell = sheet.getRow(rowNum).getCell(cellNum);

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    public int getRowByTcId(String sheetName, String tcId) {
        Sheet sheet = wb.getSheet(sheetName);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            String currentTcId = getDataFromExcel(sheetName, i, 0);

            if (currentTcId.equalsIgnoreCase(tcId)) {
                return i;
            }
        }

        throw new RuntimeException("TC_ID not found: " + tcId);
    }

    public void closeWorkbook() throws Exception {
        wb.close();
    }
}