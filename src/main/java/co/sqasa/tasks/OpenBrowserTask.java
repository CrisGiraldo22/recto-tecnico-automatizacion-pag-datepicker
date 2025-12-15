package co.sqasa.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class OpenBrowserTask implements Task {
    private static final String URL_PAGE = "https://jqueryui.com/datepicker/";

    @Override
    public <T extends Actor> void performAs(T actor){
        actor.attemptsTo(
                Open.url(URL_PAGE)

        );
    }
    public static OpenBrowserTask openPage(){
        return instrumented(OpenBrowserTask.class);
    }
}



