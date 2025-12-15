package co.sqasa.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class DatepickerPageUI {
    private DatepickerPageUI() {
        throw new IllegalStateException("Utility class");
    }
    public static final Target IFRAME_DEMO =
            Target.the("iframe del demo").located(By.cssSelector("iframe.demo-frame"));

    public static final Target INPUT_DATE =
            Target.the("input del datepicker").located(By.id("datepicker"));

    public static final Target DATEPICKER_WIDGET =
            Target.the("widget del datepicker").located(By.cssSelector("div.ui-datepicker"));

    public static final Target TITLE_MONTH_YEAR =
            Target.the("titulo mes y año").located(By.cssSelector("div.ui-datepicker-title"));

    public static final Target NEXT_MONTH =
            Target.the("siguiente mes").located(By.cssSelector("a.ui-datepicker-next"));

    public static final Target PREV_MONTH =
            Target.the("mes anterior").located(By.cssSelector("a.ui-datepicker-prev"));

    public static Target day(int day) {
        return Target.the("día " + day).locatedBy(
                "//table[contains(@class,'ui-datepicker-calendar')]//td" +
                        "[not(contains(@class,'ui-datepicker-other-month'))]" +
                        "/a[normalize-space(.)='{0}']"
        ).of(String.valueOf(day));
    }
}