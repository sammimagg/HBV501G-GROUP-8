package is.hi.hbv501g.group8.Persistence.Entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="deviations")
public final class Deviation {
    public enum TYPE {
        SICK,
        VACATION,
        OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    private String SSN;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom, dateTo;
    private TYPE type;

    public Deviation() {
    }

    /**
     * Description: Getter for ID
     *
     * @return ID
     */
    public long getID() {
        return ID;
    }

    /**
     * Description: Setter for ID
     *
     * @param ID
     */
    public void setID(long ID) {
        this.ID = ID;
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
     * @param SSN
     */
    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    /**
     * Description: Getter for DateFrom
     *
     * @return dateFrom
     */
    public LocalDate getDateFrom() {
        return dateFrom;
    }

    /**
     * Description: Setter for dateFrom
     *
     * @param dateFrom
     */
    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * Description: Getter for DateTo
     *
     * @return dateTo
     */
    public LocalDate getDateTo() {
        return dateTo;
    }

    /**
     * Description: Setter for DateTo
     *
     * @param dateTo
     */
    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * Description: Getter for type
     *
     * @return type
     */
    public TYPE getType() {
        return type;
    }

    /**
     * Description: Setter for type
     *
     * @param type
     */
    public void setType(TYPE type) {
        this.type = type;
    }
}
