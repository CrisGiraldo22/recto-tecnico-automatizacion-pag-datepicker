package co.sqasa.config;

public final class TestConfig {

    private TestConfig() {
        throw new IllegalStateException("Utility class");
    }

    // ---- Datepicker URL ----
    private static final String ENV_DATEPICKER_URL = "DATEPICKER_URL";
    private static final String SYS_DATEPICKER_URL = "datepicker.url";

    public static String datepickerUrl() {
        String url = firstNonBlank(
                System.getenv(ENV_DATEPICKER_URL),
                System.getProperty(SYS_DATEPICKER_URL)
        );

        return required(url,
                "Datepicker URL is not configured. Set environment variable DATEPICKER_URL or system property -Ddatepicker.url");
    }

    // ---- Excel Path (classpath) ----
    private static final String ENV_EXCEL_PATH = "EXCEL_DATEPICKER_PATH";
    private static final String SYS_EXCEL_PATH = "excel.datepicker.path";

    public static String excelDatepickerPath() {
        String path = firstNonBlank(
                System.getenv(ENV_EXCEL_PATH),
                System.getProperty(SYS_EXCEL_PATH)
        );

        return required(path,
                "Excel path is not configured. Set environment variable EXCEL_DATEPICKER_PATH or system property -Dexcel.datepicker.path");
    }

    // ---- Helpers ----
    private static String firstNonBlank(String a, String b) {
        if (isNotBlank(a)) return a.trim();
        if (isNotBlank(b)) return b.trim();
        return null;
    }

    private static boolean isNotBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }

    private static String required(String value, String message) {
        if (isNotBlank(value)) return value.trim();
        throw new IllegalStateException(message);
    }
}
