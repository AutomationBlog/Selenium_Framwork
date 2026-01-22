package com.amazon.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Utility class for handling Excel operations
 */
public class ExcelUtility {
    private static Workbook workbook;
    private static Sheet sheet;
    private static Row row;
    private static Cell cell;
    private String filePath;

    public ExcelUtility(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Read data from Excel file
     */
    public void readExcel() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        workbook = new XSSFWorkbook(fileInputStream);
    }

    /**
     * Get data from specific cell
     */
    public String getCellData(int sheetIndex, int rowIndex, int colIndex) throws IOException {
        readExcel();
        sheet = workbook.getSheetAt(sheetIndex);
        row = sheet.getRow(rowIndex);
        cell = row.getCell(colIndex);
        String cellData = cell.getStringCellValue();
        workbook.close();
        return cellData;
    }

    /**
     * Get data from specific cell by sheet name
     */
    public String getCellData(String sheetName, int rowIndex, int colIndex) throws IOException {
        readExcel();
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowIndex);
        cell = row.getCell(colIndex);
        String cellData = cell.getStringCellValue();
        workbook.close();
        return cellData;
    }

    /**
     * Get all data from sheet as list of maps
     */
    public List<Map<String, String>> getSheetData(String sheetName) throws IOException {
        readExcel();
        sheet = workbook.getSheet(sheetName);
        List<Map<String, String>> data = new ArrayList<>();
        int lastRowNum = sheet.getLastRowNum();
        
        // Get headers
        Row headerRow = sheet.getRow(0);
        List<String> headers = new ArrayList<>();
        for (int colIndex = 0; colIndex < headerRow.getLastCellNum(); colIndex++) {
            headers.add(headerRow.getCell(colIndex).getStringCellValue());
        }

        // Get data
        for (int rowIndex = 1; rowIndex <= lastRowNum; rowIndex++) {
            Row dataRow = sheet.getRow(rowIndex);
            if (dataRow == null) continue;

            Map<String, String> rowData = new LinkedHashMap<>();
            for (int colIndex = 0; colIndex < headers.size(); colIndex++) {
                Cell dataCell = dataRow.getCell(colIndex);
                String value = "";
                if (dataCell != null) {
                    if (dataCell.getCellType() == CellType.NUMERIC) {
                        value = String.valueOf((int) dataCell.getNumericCellValue());
                    } else {
                        value = dataCell.getStringCellValue();
                    }
                }
                rowData.put(headers.get(colIndex), value);
            }
            data.add(rowData);
        }
        workbook.close();
        return data;
    }

    /**
     * Write data to Excel file
     */
    public void writeCellData(String sheetName, int rowIndex, int colIndex, String data) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        workbook = new XSSFWorkbook(fileInputStream);
        sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
        }

        row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }

        cell = row.createCell(colIndex);
        cell.setCellValue(data);

        FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
        workbook.write(fileOutputStream);
        workbook.close();
        fileInputStream.close();
        fileOutputStream.close();
    }

    /**
     * Get row count
     */
    public int getRowCount(String sheetName) throws IOException {
        readExcel();
        sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum();
        workbook.close();
        return rowCount;
    }

    /**
     * Get column count
     */
    public int getColumnCount(String sheetName) throws IOException {
        readExcel();
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(0);
        int colCount = row.getLastCellNum();
        workbook.close();
        return colCount;
    }
}
