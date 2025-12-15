package co.sqasa.utils;

import co.sqasa.config.TestConfig;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class ReadExcel {

    private ReadExcel() {
        throw new IllegalStateException("Utility class");
    }

    private static final DateTimeFormatter OUT_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static String getDateByCase(String wantedCase) {
        return getDateByCase(wantedCase, TestConfig.excelDatepickerPath());
    }

    public static String getDateByCase(String wantedCase, String excelPathOnClasspath) {
        try (InputStream is = openFromClasspath(excelPathOnClasspath);
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            int[] cols = findCaseAndDateColumns(sheet);

            String date = findDateForCase(sheet, cols[0], cols[1], wantedCase);
            if (date != null) return date;

            throw new ExcelDataNotFoundException("The case '" + wantedCase + "' was not found in the Excel file.");

        } catch (ExcelDataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ExcelDataNotFoundException("Error reading Excel: " + e.getMessage(), e);
        }
    }

    private static InputStream openFromClasspath(String excelPathOnClasspath) {
        InputStream is = ReadExcel.class.getResourceAsStream(excelPathOnClasspath);
        if (is == null) {
            throw new ExcelDataNotFoundException("The file was not found in the classpath: " + excelPathOnClasspath);
        }
        return is;
    }

    /**
     * @return int[]{caseColumnIndex, dateColumnIndex}
     */
    private static int[] findCaseAndDateColumns(Sheet sheet) {
        Row header = sheet.getRow(0);
        if (header == null) {
            throw new ExcelDataNotFoundException("The Excel file does not have a header (row 0).");
        }

        int caseColumnIndex = findColumnIndex(header, "case");
        int dateColumnIndex = findColumnIndex(header, "date");

        if (caseColumnIndex == -1 || dateColumnIndex == -1) {
            throw new ExcelDataNotFoundException("The Excel file must have 'case' and 'date' columns in the header.");
        }

        return new int[]{caseColumnIndex, dateColumnIndex};
    }

    private static int findColumnIndex(Row header, String expectedHeaderName) {
        short lastCell = header.getLastCellNum();
        if (lastCell <= 0) return -1;

        for (int i = 0; i < lastCell; i++) {
            Cell cell = header.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell == null) continue;

            String headerValue = getCellAsString(cell).trim().toLowerCase();
            if (expectedHeaderName.equals(headerValue)) return i;
        }
        return -1;
    }

    private static String findDateForCase(Sheet sheet, int caseColumnIndex, int dateColumnIndex, String wantedCase) {
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Cell caseCell = row.getCell(caseColumnIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            String caseValue = getCellAsString(caseCell);

            if (wantedCase.equalsIgnoreCase(caseValue)) {
                Cell dateCell = row.getCell(dateColumnIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                return getDateAsString(dateCell);
            }
        }
        return null;
    }

    private static String getDateAsString(Cell cell) {
        if (cell == null) return "";

        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            LocalDate date = cell.getDateCellValue().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            return OUT_FORMAT.format(date);
        }

        return getCellAsString(cell);
    }

    private static String getCellAsString(Cell cell) {
        if (cell == null) return "";
        return new DataFormatter().formatCellValue(cell).trim();
    }
}
