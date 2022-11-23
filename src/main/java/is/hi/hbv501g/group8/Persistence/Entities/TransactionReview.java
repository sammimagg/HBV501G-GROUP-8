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

    public TransactionReview(){}

    public TransactionReview(long ID, String SSN, String status, LocalDateTime changedClockIn, LocalDateTime changedClockOut) {
        this.ID = ID;
        this.SSN = SSN;
        this.status = status;
        this.changedClockIn = changedClockIn;
        this.changedClockOut = changedClockOut;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }



    public void setStatus(String status) {
        this.status = status;
    }





    public void setChangedClockIn(LocalDateTime changedClockIn) {
        this.changedClockIn = changedClockIn;
    }



    public void setChangedClockOut(LocalDateTime changedClockOut) {
        this.changedClockOut = changedClockOut;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getChangedClockIn() {
        return changedClockIn;
    }

    public LocalDateTime getChangedClockOut() {
        return changedClockOut;
    }

    public LocalDateTime getOriginalClockIn() {
        return originalClockIn;
    }

    public void setOriginalClockIn(LocalDateTime originalClockIn) {
        this.originalClockIn = originalClockIn;
    }

    public LocalDateTime getOriginalClockOut() {
        return originalClockOut;
    }

    public void setOriginalClockOut(LocalDateTime originalClockOut) {
        this.originalClockOut = originalClockOut;
    }

    public LocalDate getClockInDate() {
        return changedClockIn.toLocalDate();
    }

    public LocalTime getOriginalClockInTime() {
        return originalClockIn.toLocalTime();
    }

    public LocalTime getOriginalClockOutTime() {
        return originalClockOut.toLocalTime();
    }

    public LocalTime getChangedClockInTime() {
        return changedClockIn.toLocalTime();
    }

    public LocalTime getChangedClockOutTime() {
        return changedClockOut.toLocalTime();
    }
}
