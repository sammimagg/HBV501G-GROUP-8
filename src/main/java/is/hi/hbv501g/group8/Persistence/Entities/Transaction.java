/**
 * Transaction
 *
 * Description: Transaction object linked to our database.
 *              Each transaction represents a clock-in and a clock-out
 *
 * @author kristófer Breki Gylfason - kbg15@hi.is
 * @author Halldór Jens Vilhjálsson - hjv6@hi.is
 * @author Samúel Magnússon - sam38@hi.is
 */
package is.hi.hbv501g.group8.Persistence.Entities;

import javax.persistence.*;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private String SSN;
    private String status;
    private LocalDateTime clockIn;
    private LocalDateTime clockOut;
    private boolean finished;

    @Transient
    private LocalDate clockInDate;
    @Transient
    private LocalTime clockInTime, clockOutTime;

    @Transient
    private Time workedTime;

    @Transient
    private int workedHours, workedMinutes;

    @Transient
    private Duration duration;

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
    * Description: Help function to tell if transaction is finished.
    *
    * @return If Clock-in and Clock-out is not null then it's true.
    * Else false
    */
    public boolean isFinished() {
        return finished;
    }

    /**
    * Description: Setter for finished of clock-in
    *
    * @param finished
    */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * Description: Setter for SSN of a transaction
     *
     * @param SSN
     */
    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    /**
     * Description: Getter for status of transaction
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Description: Setter for status of transaction
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Description: Getter for date of clock-in
     *
     * @return clockInDate
     */
    public LocalDate getClockInDate() {
        return clockInDate;
    }

    /**
     * Description: Setter for date of clock-in
     *
     * @param clockInDate LocalDate
     */
    public void setClockInDate(LocalDate clockInDate) {
        this.clockInDate = clockInDate;
    }

    /**
     * Description: Getter for time of clock-in
     *
     * @return clockInTime
     */
    public LocalTime getClockInTime() {
        return clockInTime;
    }

    /**
     * Description: Setter for time of clock-in
     *
     * @param clockInTime LocalTime
     */
    public void setClockInTime(LocalTime clockInTime) {
        this.clockInTime = clockInTime;
    }

    /**
     * Description: Getter for time of clock-out
     *
     * @return clockOutTime
     */
    public LocalTime getClockOutTime() {
        return clockOutTime;
    }

    /**
     * Description: Setter for time of clock-out
     *
     * @param clockOutTime LocalTime
     */
    public void setClockOutTime(LocalTime clockOutTime) {
        this.clockOutTime = clockOutTime;
    }

    /**
     * Description: Getter for over-all worked time in a month
     *
     * @return workedTime
     */
    public Time getWorkedTime() {
        return workedTime;
    }

    /**
     * Description: Setter for over-all worked time in a month
     *
     * @param workedTime Time
     */
    public void setWorkedTime(Time workedTime) {
        this.workedTime = workedTime;
    }

    /**
     * Description: Getter for over-all worked hours in a month
     *
     * @return workedHours
     */
    public int getWorkedHours() {
        return workedHours;
    }

    /**
     * Description: Getter for over-all worked minutes in a month
     *
     * @return workedMinutes
     */
    public int getWorkedMinutes() {
        return workedMinutes;
    }

    /**
     * Description: Setter for over-all worked hours in a month
     *
     * @param workedHours
     */
    public void setWorkedHours(int workedHours) {
        this.workedHours = workedHours;
    }

    /**
     * Description: Setter for over-all worked mintues in a month
     *
     * @param workedMinutes
     */
    public void setWorkedMinutes(int workedMinutes) {
        this.workedMinutes = workedMinutes;
    }

    /**
     * Description: Getter to change the duration between clock-in and clock-out to minutes
     *
     * @return Duration
     */
    public long getDuration() {
        return Duration.between(clockIn, clockOut).toMinutes();
    }
}
