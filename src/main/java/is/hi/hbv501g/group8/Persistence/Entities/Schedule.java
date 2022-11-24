package is.hi.hbv501g.group8.Persistence.Entities;


import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private String SSN;
    private LocalDate dateFrom, dateTo;
    private LocalTime timeFrom, timeTo;

        public void Transaction() {

    }

    /**
    * Description: Getter for ID of a transaction
    *
    * @return ID of a transaction
    */
    public long getID() {
        return ID;
    }

    /**
    * Description: Setter for ID of a transaction
    *
    * @param ID of a transaction
    */
    public void setID(long ID) {
        this.ID = ID;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalTime getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(LocalTime timeFrom) {
        this.timeFrom = timeFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public LocalTime getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(LocalTime timeTo) {
        this.timeTo = timeTo;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }
}