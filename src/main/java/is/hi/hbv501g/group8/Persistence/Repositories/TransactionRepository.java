package is.hi.hbv501g.group8.Persistence.Repositories;

import is.hi.hbv501g.group8.Persistence.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction save(Transaction transaction);


    void delete(Transaction transaction);

    List<Transaction> findAll();
    List<Transaction> findBySSN(String ssn);
    List<Transaction> findAllBySSNAndClockInBetween(String ssn, LocalDateTime startDate, LocalDateTime endDate);
    List<Transaction> findAllBySSNAndFinishedAndClockInBetween(String ssn, boolean finished, LocalDateTime startDate, LocalDateTime endDate);
    Transaction findByID(long id);
    Transaction findBySSNAndFinished(String ssn, boolean finished);
    Transaction findFirstBySSNOrderByIDDesc(String ssn);
}
