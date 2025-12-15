package co.sqasa.tasks;

import co.sqasa.config.TestConfig;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public class OpenBrowserTask {

    private OpenBrowserTask() {}

    public static Task openPage() {
        return Task.where("{0} abre la página del datepicker",
                Open.url(TestConfig.datepickerUrl())
        );
    }
}



