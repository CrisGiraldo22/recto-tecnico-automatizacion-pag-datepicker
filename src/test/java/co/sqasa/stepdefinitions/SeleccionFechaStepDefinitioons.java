package co.sqasa.stepdefinitions;

import co.sqasa.questions.DisplayedDateValue;
import co.sqasa.questions.IsDisplayedMonthDifferentFromCurrent;
import co.sqasa.tasks.OpenBrowserTask;
import co.sqasa.tasks.SeleccionarFechaDatepicker;
import co.sqasa.utils.ReadExcel;
import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.ensure.Ensure;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static net.serenitybdd.screenplay.actors.OnStage.setTheStage;

public class SeleccionFechaStepDefinitioons {

    private static final DateTimeFormatter EXCEL_FORMATO = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private static final String MEM_FECHA_STRING = "FECHA_STRING";
    private static final String MEM_FECHA_DATE = "FECHA_DATE";

    @Before
    public void configurarEscenario() {
        setTheStage(new OnlineCast());
    }

    @Dado("que la usuaria abre la pagina del datepicker")
    public void queLaUsuariaAbreLaPaginaDelDatepicker() {
        OnStage.theActorCalled("Cristina").attemptsTo(OpenBrowserTask.openPage());
    }

    @Cuando("la usuaria selecciona la fecha del mes diferente al actual definida en el archivo {string}")
    public void laUsuariaSeleccionaLaFechaDelMesDiferenteAlActualDefinidaEnElArchivo(String caseKey) {

        Actor actor = OnStage.theActorInTheSpotlight();

        String fechaStr = ReadExcel.getDateByCase(caseKey);
        LocalDate fecha = LocalDate.parse(fechaStr, EXCEL_FORMATO);
        actor.remember(MEM_FECHA_STRING, fechaStr);
        actor.remember(MEM_FECHA_DATE, fecha);

        actor.attemptsTo(SeleccionarFechaDatepicker.con(fecha));
    }

    @Entonces("la fecha mostrada en el campo corresponde a la fecha del archivo")
    public void laFechaMostradaEnElCampoCorrespondeALaFechaDelArchivo() {
        Actor actor = OnStage.theActorInTheSpotlight();
        String expected = actor.recall(MEM_FECHA_STRING);

        actor.attemptsTo(
                Ensure.that(DisplayedDateValue.value()).isEqualTo(expected)
        );
    }

    @Y("la fecha corresponde a un mes diferente a la actual")
    public void laFechaCorrespondeAUnMesDiferenteALaActual() {
        Actor actor = OnStage.theActorInTheSpotlight();

        actor.attemptsTo(
                Ensure.that(IsDisplayedMonthDifferentFromCurrent.using(
                        ZoneId.of("America/Bogota"),
                        EXCEL_FORMATO
                )).isTrue()
        );
    }
}
