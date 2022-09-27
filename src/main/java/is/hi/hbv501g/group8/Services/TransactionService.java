package is.hi.hbv501g.group8.Services;

import is.hi.hbv501g.group8.Persistence.Entities.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    Transaction save(Transaction transaction);
    void delete(Transaction transaction);

    List<Transaction> findAll();
    List<Transaction> findBySSN(String ssn);
    List<Transaction> findAllBySSNAndClockInBetween(String ssn, LocalDateTime startDate, LocalDateTime endDate);
    Transaction findByID(long id);
    Transaction findBySSNAndFinished(String ssn, boolean finished);
}
