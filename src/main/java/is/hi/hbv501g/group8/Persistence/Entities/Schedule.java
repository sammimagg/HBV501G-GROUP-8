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

    /**
     * Description: Getter for getting the date and time from clock-in
     *
     * @return dateAndTimeFrom
     */
    public LocalDateTime getDateAndTimeFrom() {
        return dateAndTimeFrom;
    }

    /**
     * Description: Getter for getting the date and time to clock-out
     *
     * @return dateAndTimeTo
     */
    public LocalDateTime getDateAndTimeTo() {
        return dateAndTimeTo;
    }

    /**
     * Description: Setter for getting the date and time from clock-in
     *
     * @param dateAndTimeFrom LocalDateTime
     */
    public void setDateAndTimeFrom(LocalDateTime dateAndTimeFrom) {
        this.dateAndTimeFrom = dateAndTimeFrom;
    }

    /**
     * Description: Setter for getting the date and time to clock-out
     *
     * @param dateAndTimeTo LocalDateTime
     */
    public void setDateAndTimeTo(LocalDateTime dateAndTimeTo) {
        this.dateAndTimeTo = dateAndTimeTo;
    }

    /**
     * Description: Getter for SSN
     *
     * @return SSN
     */
    public String getSSN() {
        return SSN;
    }

    /**
     * Description: Setter for SSN
     *
     * @param SSN String
     */
    public void setSSN(String SSN) {
        this.SSN = SSN;
    }
}