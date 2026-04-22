package utils.excelUtility;

import java.io.InputStream;
import org.apache.poi.ss.usermodel.*;

public class ExcelUtilityForCreate {

    private Workbook wb;

    public ExcelUtilityForCreate(String fileName) throws Exception {

        InputStream fis = getClass().getClassLoader()
                .getResourceAsStream("testdata/" + fileName);

        if (fis == null) {
            throw new RuntimeException("File not found in testdata folder: " + fileName);
        }

        wb = WorkbookFactory.create(fis);
    }

    public String getDataFromExcel(String sheetName, int rowNum, int cellNum) {

        Sheet sheet = wb.getSheet(sheetName);
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(cellNum);

        switch (cell.getCellType()) {

            case STRING:
                return cell.getStringCellValue().trim();

            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            default:
                return "";
        }
    }

    public int getRowByScenario(String sheetName, String scenario) {

        Sheet sheet = wb.getSheet(sheetName);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            String scenarioName = getDataFromExcel(sheetName, i, 0);

            if (scenarioName.equalsIgnoreCase(scenario)) {
                return i;
            }
        }

        throw new RuntimeException("Scenario not found: " + scenario);
    }

    public int getRowCount(String sheetName) {

        Sheet sheet = wb.getSheet(sheetName);
        return sheet.getLastRowNum();
    }

    public void closeWorkbook() throws Exception {
        wb.close();
    }
}