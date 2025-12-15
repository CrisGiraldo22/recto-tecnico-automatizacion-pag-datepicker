package co.sqasa.utils;

public class ExcelDataNotFoundException extends RuntimeException {
    public ExcelDataNotFoundException(String message) {
        super(message);
    }

    public ExcelDataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}