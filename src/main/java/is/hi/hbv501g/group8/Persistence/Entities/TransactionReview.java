package is.hi.hbv501g.group8.Persistence.Entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="transactionreviews")
public class TransactionReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private String SSN, status;
    private LocalDateTime originalClockIn, originalClockedOut, changedClockIn, changedClockOut;

    public TransactionReview(){}

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOriginalClockIn() {
        return originalClockIn;
    }

    public void setOriginalClockIn(LocalDateTime originalClockIn) {
        this.originalClockIn = originalClockIn;
    }

    public LocalDateTime getOriginalClockedOut() {
        return originalClockedOut;
    }

    public void setOriginalClockedOut(LocalDateTime originalClockedOut) {
        this.originalClockedOut = originalClockedOut;
    }

    public LocalDateTime getChangedClockIn() {
        return changedClockIn;
    }

    public void setChangedClockIn(LocalDateTime changedClockIn) {
        this.changedClockIn = changedClockIn;
    }

    public LocalDateTime getChangedClockOut() {
        return changedClockOut;
    }

    public void setChangedClockOut(LocalDateTime changedClockOut) {
        this.changedClockOut = changedClockOut;
    }
}
