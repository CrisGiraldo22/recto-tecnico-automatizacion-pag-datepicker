package co.sqasa.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class AbrirNavegador implements Task {
    private static final String urlPage = "https://jqueryui.com/datepicker/";

    @Override
    public <T extends Actor> void performAs(T actor){
        actor.attemptsTo(
                Open.url(urlPage)

        );
    }
    public static AbrirNavegadorTask openPage(){
        return instrumented(AbrirNavegadorTask.class);
    }
}



