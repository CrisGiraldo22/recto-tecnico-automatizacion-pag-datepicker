package co.sqasa.stepdefinitions;

import co.sqasa.tasks.AbrirNavegadorTask;
import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import static net.serenitybdd.screenplay.actors.OnStage.setTheStage;

public class SeleccionFechaStepDefinitioons {
    @Before
    public void ConfigurarEscenario(){
        setTheStage(new OnlineCast());
    }
    @Dado("que la usuaria abre la pagina del datepicker")
    public void queLaUsuariaAbreLaPaginaDelDatepicker() {
        OnStage.theActorCalled("Navegar a la pagina airbnb").attemptsTo(AbrirNavegadorTask.openPage());
    }

    @Cuando(
            "la usuaria selecciona la fecha del mes diferente al actual definida en el archivo {string}")
    public void laUsuariaSeleccionaLaFechaDelMesDiferenteAlActualDefinidaEnElArchivo(String fecha_mes_diferenter_xlsx) {
        
    }
/*
    @Entonces("la fecha mostrada en el campo corresponde a la fecha del archivo")
    public void laFechaMostradaEnElCampoCorrespondeALaFechaDelArchivo() {
        
    }

    @Y("la fecha corresponde a un mes diferente a la actual")
    public void laFechaCorrespondeAUnMesDiferenteALaActual() {
    }*/
}
