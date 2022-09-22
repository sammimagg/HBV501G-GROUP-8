package is.hi.hbv501g.group8.Persistence.Repositories;

import is.hi.hbv501g.group8.Persistence.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction save(Transaction transaction);
    void delete(Transaction transaction);

    List<Transaction> findAll();
    List<Transaction> findBySSN(String ssn);
    Transaction findByID(long id);
    Transaction findBySSNAndFinished(String ssn, boolean finished);
}
