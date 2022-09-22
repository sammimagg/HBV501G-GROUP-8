package is.hi.hbv501g.group8.Persistence.Entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private String SSN;
    private LocalDateTime clockIn;
    private LocalDateTime clockOut;
    private boolean finished;

    public Transaction() {

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

    public void setSsn(String ssn) {
        this.SSN = ssn;
    }

    public LocalDateTime getClockIn() {
        return clockIn;
    }

    public void setClockIn(LocalDateTime clockIn) {
        this.clockIn = clockIn;
    }

    public LocalDateTime getClockOut() {
        return clockOut;
    }

    public void setClockOut(LocalDateTime clockOut) {
        this.clockOut = clockOut;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
