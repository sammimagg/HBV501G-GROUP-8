package is.hi.hbv501g.group8.Persistence.Entities;

public class MappedRequestUserDAO {
    private TransactionReview transactionReview;
    private Employee employee;

    public MappedRequestUserDAO(TransactionReview transactionReview, Employee employee) {
        this.transactionReview = transactionReview;
        this.employee = employee;
    }

    public TransactionReview getTransactionReview() {
        return transactionReview;
    }

    public void setTransactionReview(TransactionReview transactionReview) {
        this.transactionReview = transactionReview;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
