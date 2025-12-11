package co.sqasa.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class LeerExcel {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Lee la fecha definida en el archivo de excel
     * Columnaas "caso" y "fecha"
     */

    public static LocalDate seleccionarFecha(String rutaRecurso,
                                                   String nombreHoja,
                                                   String nombreCaso) {

        try (InputStream inputStream = LeerExcel.class
                .getClassLoader()
                .getResourceAsStream(rutaRecurso);
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheet(nombreHoja);
            if (sheet == null) {
                throw new IllegalArgumentException("No se encontró la hoja: " + nombreHoja);
            }

            int columnaCaso = -1;
            int columnaFecha = -1;

            Iterator<Row> rowIterator = sheet.iterator();
            if (!rowIterator.hasNext()) {
                throw new IllegalStateException("La hoja " + nombreHoja + " está vacía");
            }

            // Encabezados
            Row headerRow = rowIterator.next();
            for (Cell cell : headerRow) {
                String header = cell.getStringCellValue().trim().toLowerCase();
                if (header.equals("caso")) {
                    columnaCaso = cell.getColumnIndex();
                } else if (header.equals("fecha")) {
                    columnaFecha = cell.getColumnIndex();
                }
            }

            if (columnaCaso == -1 || columnaFecha == -1) {
                throw new IllegalStateException("No se encontraron las columnas 'caso' y 'fecha' en el Excel");
            }

            // Fila
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Cell cellCaso = row.getCell(columnaCaso);
                if (cellCaso == null) continue;

                String valorCaso = cellCaso.getStringCellValue().trim();
                if (nombreCaso.equalsIgnoreCase(valorCaso)) {

                    Cell cellFecha = row.getCell(columnaFecha);
                    if (cellFecha == null) {
                        throw new IllegalStateException("La celda de fecha está vacía para el caso: " + nombreCaso);
                    }

                    if (cellFecha.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cellFecha)) {
                        // Si es fecha de Excel
                        return cellFecha.getLocalDateTimeCellValue().toLocalDate();
                    } else {
                        // Si es texto
                        String fechaTexto = cellFecha.getStringCellValue().trim();
                        return LocalDate.parse(fechaTexto, FORMATTER);
                    }
                }
            }

            throw new IllegalStateException("No se encontró el caso: " + nombreCaso);

        } catch (Exception e) {
            throw new RuntimeException("Error leyendo el Excel de datos: " + e.getMessage(), e);
        }
    }
}




