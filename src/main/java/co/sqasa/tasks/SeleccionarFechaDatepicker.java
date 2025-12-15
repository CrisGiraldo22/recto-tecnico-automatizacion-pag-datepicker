package co.sqasa.tasks;

import co.sqasa.userinterfaces.DatepickerPageUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;

public class SeleccionarFechaDatepicker implements Task {

    private final LocalDate fechaObjetivo;

    public SeleccionarFechaDatepicker(LocalDate fechaObjetivo) {
        this.fechaObjetivo = fechaObjetivo;
    }

    public static SeleccionarFechaDatepicker con(LocalDate fechaObjetivo) {
        return new SeleccionarFechaDatepicker(fechaObjetivo);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        WebDriver driver = BrowseTheWeb.as(actor).getDriver();

        // 1) Asegurar que estamos en el DOM principal
        driver.switchTo().defaultContent();

        // 2) Entrar al iframe del demo (jqueryui)
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe.demo-frame")));

        // 3) Abrir datepicker
        actor.attemptsTo(
                WaitUntil.the(DatepickerPageUI.INPUT_DATE, isClickable()),
                Click.on(DatepickerPageUI.INPUT_DATE),
                WaitUntil.the(DatepickerPageUI.DATEPICKER_WIDGET, isVisible())
        );

        YearMonth targetYM = YearMonth.from(fechaObjetivo);

        // Ej: "February 2025"
        DateTimeFormatter titleFmt = DateTimeFormatter.ofPattern("MMMM uuuu", Locale.ENGLISH);

        // Evitar loops infinitos
        for (int i = 0; i < 36; i++) {
            String titleRaw = Text.of(DatepickerPageUI.TITLE_MONTH_YEAR).answeredBy(actor);
            String title = titleRaw.trim().replaceAll("\\s+", " ");

            YearMonth currentYM = YearMonth.parse(title, titleFmt);

            if (currentYM.equals(targetYM)) break;

            if (currentYM.isBefore(targetYM)) {
                actor.attemptsTo(Click.on(DatepickerPageUI.NEXT_MONTH));
            } else {
                actor.attemptsTo(Click.on(DatepickerPageUI.PREV_MONTH));
            }
        }

        // Seleccionar día
        int day = fechaObjetivo.getDayOfMonth();
        actor.attemptsTo(
                WaitUntil.the(DatepickerPageUI.day(day), isClickable()),
                Click.on(DatepickerPageUI.day(day))
        );
    }
}