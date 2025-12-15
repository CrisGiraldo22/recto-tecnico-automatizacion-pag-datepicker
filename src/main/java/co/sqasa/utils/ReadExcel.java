package co.sqasa.utils;

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

    private static final String DEFAULT_EXCEL_PATH = "/data/DatosPruebaDatepicker.xlsx";
    private static final String EXCEL_PATH_SYS_PROP = "excel.datepicker.path";

    private static final DateTimeFormatter OUT_FMT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static String getFechaPorCaso(String casoBuscado) {
        return getFechaPorCaso(casoBuscado, resolveExcelPath());
    }

    public static String getFechaPorCaso(String casoBuscado, String excelPathOnClasspath) {
        try (InputStream is = openFromClasspath(excelPathOnClasspath);
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            int[] cols = findCasoAndFechaColumns(sheet);

            String fecha = findFechaForCaso(sheet, cols[0], cols[1], casoBuscado);
            if (fecha != null) {
                return fecha;
            }

            throw new ExcelDataNotFoundException("No se encontró el caso '" + casoBuscado + "' en el Excel.");

        } catch (ExcelDataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ExcelDataNotFoundException("Error leyendo Excel: " + e.getMessage(), e);
        }
    }

    // ---- Helpers (reduce complejidad) ----

    private static String resolveExcelPath() {
        return System.getProperty(EXCEL_PATH_SYS_PROP, DEFAULT_EXCEL_PATH);
    }

    private static InputStream openFromClasspath(String excelPathOnClasspath) {
        InputStream is = ReadExcel.class.getResourceAsStream(excelPathOnClasspath);
        if (is == null) {
            throw new ExcelDataNotFoundException("No se encontró el archivo en classpath: " + excelPathOnClasspath);
        }
        return is;
    }

    /**
     * @return int[]{colCaso, colFecha}
     */
    private static int[] findCasoAndFechaColumns(Sheet sheet) {
        Row header = sheet.getRow(0);
        if (header == null) {
            throw new ExcelDataNotFoundException("El Excel no tiene header (fila 0).");
        }

        int colCaso = -1;
        int colFecha = -1;

        for (Cell cell : header) {
            String h = getCellAsString(cell).trim().toLowerCase();
            if ("caso".equals(h)) colCaso = cell.getColumnIndex();
            if ("fecha".equals(h)) colFecha = cell.getColumnIndex();
        }

        if (colCaso == -1 || colFecha == -1) {
            throw new ExcelDataNotFoundException("El Excel debe tener columnas 'caso' y 'fecha' en el header.");
        }

        return new int[]{colCaso, colFecha};
    }

    private static String findFechaForCaso(Sheet sheet, int colCaso, int colFecha, String casoBuscado) {
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            String caso = getCellAsString(row.getCell(colCaso));
            if (casoBuscado.equalsIgnoreCase(caso)) {
                return getFechaComoString(row.getCell(colFecha));
            }
        }
        return null;
    }

    private static String getFechaComoString(Cell cell) {
        if (cell == null) return "";

        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            LocalDate date = cell.getDateCellValue().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            return OUT_FMT.format(date);
        }

        return getCellAsString(cell);
    }

    private static String getCellAsString(Cell cell) {
        if (cell == null) return "";
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }
}
