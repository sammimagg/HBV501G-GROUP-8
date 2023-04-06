package is.hi.hbv501g.group8.Persistence.Entities;

import java.util.List;

public class ViewTransactionUserDAO {
    SessionUser sessionUser; // Replace with SessionUser
    Double total_hours; // Calculated from array of transactions
    List<Transaction> transactionList;

    public SessionUser getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(SessionUser sessionUser) {
        this.sessionUser = sessionUser;
    }

    public Double getTotal_hours() {
        return total_hours;
    }

    public void setTotal_hours(Double total_hours) {
        this.total_hours = total_hours;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}
