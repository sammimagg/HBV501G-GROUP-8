/**
 * Transaction
 *
 * Description:
 *
 * @author kristófer Breki Gylfason - kbg15@hi.is
 * @author Halldór Jens Vilhjálsson - hjv6@hi.is
 * @author Samúel Magnússon - sam38@hi.is
 */
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
     * Description: Getter for SSN in a transaction
     *
     * @return SSN of current transaction.
     */
    public String getSSN() {
        return SSN;
    }

    /**
     * Description: Setter for SSN of a transaction
     *
     * @param ssn for the transaction.
     */
    public void setSsn(String ssn) {
        this.SSN = ssn;
    }

    /**
     * Description: Getter for time and date of Clock in
     *
     * @return Time and date of a transaction.
     */
    public LocalDateTime getClockIn() {
        return clockIn;
    }
    /**
     * Description: Setter for time and date of Clock in.
     *
     * @param clockIn Current time and date of Clock in
     */
    public void setClockIn(LocalDateTime clockIn) {
        this.clockIn = clockIn;
    }
    /**
     * Description: Getter for time and date of Clock out.
     *
     * @return time and date of clock out
     */
    public LocalDateTime getClockOut() {
        return clockOut;
    }

    /**
     * Description: Setter for time and date of Clock put
     *
     * @param clockOut current time and date of a clock out.
     */
    public void setClockOut(LocalDateTime clockOut) {
        this.clockOut = clockOut;
    }

    /**
     * Description: Help function to tell if transaction is finnished.
     *
     * @return If Clock-in and Clock-out is not null then it's true.
     * Else false
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Description:
     *
     * @param finished
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
