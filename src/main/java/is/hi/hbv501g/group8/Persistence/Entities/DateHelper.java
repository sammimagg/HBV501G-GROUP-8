/**
 * Data Helper
 *
 * Description: Assistance class for Date Formatting.
 *
 * @author kristófer Breki Gylfason - kbg15@hi.is
 * @author Halldór Jens Vilhjálsson - hjv6@hi.is
 * @author Samúel Magnússon - sam38@hi.is
 *
 * @see is.hi.hbv501g.group8.Persistence.Entities.TransactionReview
 */
package is.hi.hbv501g.group8.Persistence.Entities;

import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class DateHelper {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date1;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date2;

    /**
     * Description: LocalDate for both dates
     *
     * @param date1
     * @param date2
     */
    public DateHelper(LocalDate date1, LocalDate date2) {
        this.date1 = date1;
        this.date2 = date2;
    }

    /**
     * Description: Getter for the first date, Date1
     *
     * @return
     */
    public LocalDate getDate1() {
        return date1;
    }

    /**
     * Description: Setter for the first date, Date1
     *
     * @param date1
     */
    public void setDate1(LocalDate date1) {
        this.date1 = date1;
    }

    /**
     * Description: Getter for the second date, Date2
     *
     * @return
     */
    public LocalDate getDate2() {
        return date2;
    }

    /**
     * Description: Setter for the second date, Date2
     *
     * @param date2
     */
    public void setDate2(LocalDate date2) {
        this.date2 = date2;
    }
}
