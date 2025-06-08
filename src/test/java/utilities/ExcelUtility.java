package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtility {

    private Workbook workbook;
    private Sheet sheet;

    public ExcelUtility(String filePath, String sheetName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(fileInputStream);
        sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            throw new RuntimeException("Sheet '" + sheetName + "' not found in the Excel file");
        }
    }

    public List<Object[]> getTestData() {
        List<Object[]> testData = new ArrayList<>();

        // Get the number of rows (excluding header)
        int rowCount = sheet.getLastRowNum();

        // Start from row 1 (skip header row 0)
        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                String username = getCellValue(row.getCell(0));
                String password = getCellValue(row.getCell(1));
                String expectedResult = getCellValue(row.getCell(2));

                // Skip empty rows
                if (!username.isEmpty()) {
                    testData.add(new Object[]{username, password, expectedResult});
                }
            }
        }

        return testData;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    public void closeWorkbook() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing workbook: " + e.getMessage());
        }
    }

    // Method to get data as 2D array for TestNG DataProvider
    public Object[][] getTestDataArray() {
        List<Object[]> testDataList = getTestData();
        Object[][] testDataArray = new Object[testDataList.size()][];

        for (int i = 0; i < testDataList.size(); i++) {
            testDataArray[i] = testDataList.get(i);
        }

        return testDataArray;
    }
}