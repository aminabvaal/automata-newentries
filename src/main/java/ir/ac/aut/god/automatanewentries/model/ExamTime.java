package ir.ac.aut.god.automatanewentries.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.time4j.calendar.PersianCalendar;

import java.time.LocalDate;

/**
 * created By aMIN on 5/19/2019 7:04 PM
 */


@Getter
@Setter
@Accessors(chain = true)
public final class ExamTime {

    private int day;
    private int month;
    private int year;
    private int startTime;
    private int finishTime;

    private PersianCalendar persianDate;

    public ExamTime() {

    }

}
