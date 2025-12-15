package co.sqasa.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class IsDisplayedMonthDifferentFromCurrent implements Question<Boolean> {

    private final ZoneId zoneId;
    private final DateTimeFormatter formatter;

    private IsDisplayedMonthDifferentFromCurrent(ZoneId zoneId, DateTimeFormatter formatter) {
        this.zoneId = zoneId;
        this.formatter = formatter;
    }

    public static IsDisplayedMonthDifferentFromCurrent using(ZoneId zoneId, DateTimeFormatter formatter) {
        return new IsDisplayedMonthDifferentFromCurrent(zoneId, formatter);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        String value = DisplayedDateValue.value().answeredBy(actor);
        LocalDate selected = LocalDate.parse(value, formatter);

        YearMonth selectedMonth = YearMonth.from(selected);
        YearMonth currentMonth = YearMonth.now(zoneId);

        return !selectedMonth.equals(currentMonth);
    }
}
