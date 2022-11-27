/**
 * RequestReview
 *
 * Description: TransactionReview object linked to our database.
 *              ??????
 *
 * @author kristófer Breki Gylfason - kbg15@hi.is
 * @author Halldór Jens Vilhjálsson - hjv6@hi.is
 * @author Samúel Magnússon - sam38@hi.is
 */

package is.hi.hbv501g.group8.Persistence.Entities;

public class TimeAndDate {

    String dateOne, dateTwo, timeOne, timeTwo;



    String id;
    public TimeAndDate(){

    }
    /**
     * Description: Getter for date one
     *
     * @return dateOne
     */
    public String getDateOne() {
        return dateOne;
    }
    /**
     * Description: Getter for date two
     *
     * @return dateTwo
     */
    public String getDateTwo() {
        return dateTwo;
    }

    /**
     * Description: Getter for time one
     *
     * @return timeOne
     */
    public String getTimeOne() {
        return timeOne;
    }

    /**
     * Description: Getter for time two
     *
     * @return timeTwo
     */
    public String getTimeTwo() {
        return timeTwo;
    }

    /**
     * Description: Getter for id
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Description: Setter for date one
     *
     * @param dateOne String
     */
    public void setDateOne(String dateOne) {
        this.dateOne = dateOne;
    }

    /**
     * Description: Setter for date two
     *
     * @param dateTwo String
     */
    public void setDateTwo(String dateTwo) {this.dateTwo = dateTwo;}

    /**
     * Description: Setter for time one
     *
     * @param timeOne String
     */
    public void setTimeOne(String timeOne) {
        this.timeOne = timeOne;
    }

    /**
     * Description: Setter for time two
     *
     * @param timeTwo String
     */
    public void setTimeTwo(String timeTwo) {
        this.timeTwo = timeTwo;
    }

    /**
     * Description: Setter for id
     *
     * @param id String
     */
    public void setId(String id) {
        this.id = id;
    }
}
