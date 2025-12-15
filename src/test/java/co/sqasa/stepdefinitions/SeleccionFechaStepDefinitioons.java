package co.sqasa.stepdefinitions;

import co.sqasa.tasks.OpenBrowserTask;
import co.sqasa.tasks.SeleccionarFechaDatepicker;
import co.sqasa.utils.ReadExcel;
import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static net.serenitybdd.screenplay.actors.OnStage.setTheStage;

public class SeleccionFechaStepDefinitioons {

    private static final DateTimeFormatter EXCEL_FMT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    @Before
    public void ConfigurarEscenario(){
        setTheStage(new OnlineCast());
    }
    @Dado("que la usuaria abre la pagina del datepicker")
    public void queLaUsuariaAbreLaPaginaDelDatepicker() {
        OnStage.theActorCalled("Crsitina navega a Datepicker").attemptsTo(OpenBrowserTask.openPage());
    }

    @Cuando(
            "la usuaria selecciona la fecha del mes diferente al actual definida en el archivo {string}")
    public void laUsuariaSeleccionaLaFechaDelMesDiferenteAlActualDefinidaEnElArchivo(String fecha_mes_diferenter_xlsx) {
        String fechaStr = ReadExcel.getFechaPorCaso(fecha_mes_diferenter_xlsx);     // ej: 02/15/2025
        LocalDate fecha = LocalDate.parse(fechaStr, EXCEL_FMT);

        OnStage.theActorInTheSpotlight().attemptsTo(
                SeleccionarFechaDatepicker.con(fecha)
        );
    }
        

/*
    @Entonces("la fecha mostrada en el campo corresponde a la fecha del archivo")
    public void laFechaMostradaEnElCampoCorrespondeALaFechaDelArchivo() {
        
    }

    @Y("la fecha corresponde a un mes diferente a la actual")
    public void laFechaCorrespondeAUnMesDiferenteALaActual() {
    }*/
}
