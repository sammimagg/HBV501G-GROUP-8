/**
 * TransactionReview
 *
 * Description: TransactionReview object linked to our database.
 *              TransactionReview references a Transaction in the DB.
 *              TransactionReview is used to request reviews to worked hours
 *
 * @author kristófer Breki Gylfason - kbg15@hi.is
 * @author Halldór Jens Vilhjálsson - hjv6@hi.is
 * @author Samúel Magnússon - sam38@hi.is
 *
 * References:
 * @see is.hi.hbv501g.group8.Persistence.Entities.Transaction
 */

package is.hi.hbv501g.group8.Persistence.Entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="transactionreviews")
public class TransactionReview {

    @Id
    private long ID;
    private String SSN, status;
    private LocalDateTime changedClockIn, changedClockOut;

    private LocalDateTime originalClockIn, originalClockOut;

    @Transient
    private LocalDate clockInDate;
    @Transient
    private LocalTime originalClockInTime, originalClockOutTime, changedClockInTime, changedClockOutTime;

    public TransactionReview(){

    }

    /**
     * Description: Creates a TransactionReview with parameters needed to be fulfilled
     *
     * @param ID long
     * @param SSN String
     * @param status String
     * @param changedClockIn LocalDateTime
     * @param changedClockOut LocalDateTime
     */
    public TransactionReview(long ID, String SSN, String status, LocalDateTime changedClockIn, LocalDateTime changedClockOut) {
        this.ID = ID;
        this.SSN = SSN;
        this.status = status;
        this.changedClockIn = changedClockIn;
        this.changedClockOut = changedClockOut;
    }

    /**
     * Description: Getter for ID of transaction-review
     *
     * @return ID
     */
    public long getID() {
        return ID;
    }

    /**
     * Description: Setter for ID of transaction-review
     *
     * @param ID long
     */
    public void setID(long ID) {
        this.ID = ID;
    }

    /**
     * Description: Getter for SSN of transaction-review
     *
     * @return SSN
     */
    public String getSSN() {
        return SSN;
    }

    /**
     * Description: Setter for SSN of transaction-review
     *
     * @param SSN String
     */
    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    /**
     * Description: Setter for status of transaction-review
     *
     * @param status String
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Description: Setter for changing the clock-in value of transaction-review
     *
     * @param changedClockIn LocalDateTime
     */
    public void setChangedClockIn(LocalDateTime changedClockIn) {
        this.changedClockIn = changedClockIn;
    }

    /**
     * Description: Setter for changing the clock-out value of transaction-review
     *
     * @param changedClockOut LocalDateTime
     */
    public void setChangedClockOut(LocalDateTime changedClockOut) {
        this.changedClockOut = changedClockOut;
    }

    /**
     * Description: Getter for status of transaction-review
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Description: Getter for changing the clock-in value of transaction-review
     *
     * @return changedClockIn
     */
    public LocalDateTime getChangedClockIn() {
        return changedClockIn;
    }

    /**
     * Description: Getter for changing the clock-out value of transaction-review
     *
     * @return changedClockOut
     */
    public LocalDateTime getChangedClockOut() {
        return changedClockOut;
    }

    /**
     * Description: Getter for the original time of clock-in value of transaction-review
     *
     * @return originalClockIn
     */
    public LocalDateTime getOriginalClockIn() {
        return originalClockIn;
    }

    /**
     * Description: Setter for the original time of clock-in value of transaction-review
     *
     * @param originalClockIn LocalDateTime
     */
    public void setOriginalClockIn(LocalDateTime originalClockIn) {
        this.originalClockIn = originalClockIn;
    }

    /**
     * Description: Getter for the original time of clock-out value of transaction-review
     *
     * @return originalClockOut
     */
    public LocalDateTime getOriginalClockOut() {
        return originalClockOut;
    }

    /**
     * Description: Setter for the original time of clock-out value of transaction-review
     *
     * @param originalClockOut LocalDateTime
     */
    public void setOriginalClockOut(LocalDateTime originalClockOut) {
        this.originalClockOut = originalClockOut;
    }

    /**
     * Description: Getter for the date of clock-in value of transaction-review
     *
     * @return changedClockIn.toLocalDate()
     */
    public LocalDate getClockInDate() {
        return changedClockIn.toLocalDate();
    }

    /**
     * Description: Getter for the original time of clock-in value of transaction-review
     *
     * @return originalClockIn.ToLocalTime()
     */
    public LocalTime getOriginalClockInTime() {
        return originalClockIn.toLocalTime();
    }

    /**
     * Description: Getter for the original time of clock-out value of transaction-review
     *
     * @return originalClockOut.ToLocalTime()
     */
    public LocalTime getOriginalClockOutTime() {
        return originalClockOut.toLocalTime();
    }

    /**
     * Description: Getter for the change time value of clock-in, transaction-review
     *
     * @return changedClockIn.ToLocalTime()
     */
    public LocalTime getChangedClockInTime() {
        return changedClockIn.toLocalTime();
    }

    /**
     * Description: Getter for the change time value of clock-out, transaction-review
     *
     * @return changedClockOut.ToLocalTime()
     */
    public LocalTime getChangedClockOutTime() {
        return changedClockOut.toLocalTime();
    }
}
