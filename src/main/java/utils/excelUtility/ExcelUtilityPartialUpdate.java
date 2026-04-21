package utils.excelUtility;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;

public class ExcelUtilityPartialUpdate {

    String path = System.getProperty("user.dir") + "/src/test/resources/testdata/PartialUpdationData.xlsx";

    public String getDataFromExcel(String sheetName, int rowNum, int cellNum) {

        try {
            FileInputStream fis = new FileInputStream(path);
            Workbook wb = WorkbookFactory.create(fis);

            // ✅ Get Sheet
            Sheet sheet = wb.getSheet(sheetName);

            if (sheet == null) {
                wb.close();
                fis.close();
                throw new RuntimeException("❌ Sheet NOT FOUND: " + sheetName);
            }

            // ✅ Get Row
            Row row = sheet.getRow(rowNum);

            if (row == null) {
                wb.close();
                fis.close();
                throw new RuntimeException("❌ Row NOT FOUND: " + rowNum);
            }

            // ✅ Get Cell
            Cell cell = row.getCell(cellNum);

            if (cell == null) {
                wb.close();
                fis.close();
                return "";
            }

            // ✅ Read Value
            DataFormatter formatter = new DataFormatter();
            String value = formatter.formatCellValue(cell);

            wb.close();
            fis.close();

            return value;

        } catch (IOException e) {
            throw new RuntimeException("❌ Excel Read Error: " + e.getMessage());
        }
    }
}