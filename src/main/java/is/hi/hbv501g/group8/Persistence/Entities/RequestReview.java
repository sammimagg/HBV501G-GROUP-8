package is.hi.hbv501g.group8.Persistence.Entities;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

public class RequestReview {

    String date;
    String timeIn;
    String timeOut;
    String id;
    public RequestReview(){

    }


    public String getDate() {
        return date;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public String getId() {
        return id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public void setId(String id) {
        this.id = id;
    }
}
