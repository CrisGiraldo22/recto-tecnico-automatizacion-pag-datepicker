package co.sqasa.config;

public final class TestConfig {
    private TestConfig() {
        throw new IllegalStateException("Utility class");
    }

    private static final String DEFAULT_DATEPICKER_URL = "https://jqueryui.com/datepicker/";
    private static final String ENV_DATEPICKER_URL = "DATEPICKER_URL";

    public static String datepickerUrl() {
        String envUrl = System.getenv(ENV_DATEPICKER_URL);
        if (envUrl != null && !envUrl.trim().isEmpty()) {
            return envUrl.trim();
        }
        return DEFAULT_DATEPICKER_URL;
    }
}

