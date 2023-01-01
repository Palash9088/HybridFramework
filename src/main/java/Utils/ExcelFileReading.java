package Utils;//import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelFileReading {
    static Workbook myWorkBook;

    static private void loadExcelSheet(String path) {
        File file = new File(path);
        try {
            FileInputStream inputStream = new FileInputStream(file);
            myWorkBook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Object[][] getAllRows(String path, String sheetName) {
        loadExcelSheet(path);
        Sheet sheet = myWorkBook.getSheet(sheetName);
        int totalRows = sheet.getLastRowNum();
        int totalColumn = sheet.getRow(sheet.getFirstRowNum()).getLastCellNum();
        Object[][] data = new Object[totalRows][totalColumn];
        for (int rowIndex = 1; rowIndex <= totalRows; rowIndex++) {
            for (int colIndex = 0; colIndex < totalColumn; colIndex++) {
                Cell cell = sheet.getRow(rowIndex).getCell(colIndex);
                DataFormatter dataFormatter = new DataFormatter();
                try {
                    if (CellType.NUMERIC == cell.getCellType()) {
                        data[rowIndex-1][colIndex] = dataFormatter.formatCellValue(cell);
                    } else if (CellType.STRING == cell.getCellType())
                        data[rowIndex-1][colIndex] = cell.getStringCellValue();
                    else
                        data[rowIndex-1][colIndex] = " ";
                } catch (NullPointerException ne) {
                    data[rowIndex-1][colIndex] = " ";
                }
            }
        }
        return data;
    }
}
