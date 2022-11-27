package is.hi.hbv501g.group8.Persistence.Entities;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private String SSN;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateAndTimeFrom, dateAndTimeTo;


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

    public LocalDateTime getDateAndTimeFrom() {
        return dateAndTimeFrom;
    }

    public LocalDateTime getDateAndTimeTo() {
        return dateAndTimeTo;
    }

    public void setDateAndTimeFrom(LocalDateTime dateAndTimeFrom) {
        this.dateAndTimeFrom = dateAndTimeFrom;
    }

    public void setDateAndTimeTo(LocalDateTime dateAndTimeTo) {
        this.dateAndTimeTo = dateAndTimeTo;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }
}