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
    public String getDateOne() {
        return dateOne;
    }
    public String getDateTwo() {
        return dateTwo;
    }

    public String getTimeOne() {
        return timeOne;
    }

    public String getTimeTwo() {
        return timeTwo;
    }

    public String getId() {
        return id;
    }

    public void setDateOne(String dateOne) {
        this.dateOne = dateOne;
    }
    public void setDateTwo(String dateTwo) {this.dateTwo = dateTwo;}

    public void setTimeOne(String timeOne) {
        this.timeOne = timeOne;
    }

    public void setTimeTwo(String timeTwo) {
        this.timeTwo = timeTwo;
    }

    public void setId(String id) {
        this.id = id;
    }
}
