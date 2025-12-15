package co.sqasa.questions;

import co.sqasa.userinterfaces.DatepickerPageUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.questions.Attribute;
import org.openqa.selenium.WebDriver;

public class DisplayedDateValue implements Question<String> {

    public static DisplayedDateValue value() {
        return new DisplayedDateValue();
    }

    @Override
    public String answeredBy(Actor actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        driver.switchTo().defaultContent();
        driver.switchTo().frame(driver.findElement(DatepickerPageUI.IFRAME_DEMO_BY));

        return Attribute.of(DatepickerPageUI.INPUT_DATE).named("value").answeredBy(actor);
    }
}
