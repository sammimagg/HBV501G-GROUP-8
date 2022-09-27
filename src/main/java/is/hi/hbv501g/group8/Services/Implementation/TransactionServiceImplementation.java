package is.hi.hbv501g.group8.Services.Implementation;

import is.hi.hbv501g.group8.Persistence.Entities.Transaction;
import is.hi.hbv501g.group8.Persistence.Repositories.TransactionRepository;
import is.hi.hbv501g.group8.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImplementation implements TransactionService {
    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImplementation(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // eins og signup bara
    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public void delete(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> findBySSN(String ssn) {
        return transactionRepository.findBySSN(ssn);
    }

    @Override
    public Transaction findByID(long id) {
        return transactionRepository.findByID(id);
    }

    @Override
    public Transaction findBySSNAndFinished(String ssn, boolean finished) {
        return transactionRepository.findBySSNAndFinished(ssn, finished);
    }

    @Override
    public List<Transaction> findAllBySSNAndClockInBetween(String ssn, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findAllBySSNAndClockInBetween(ssn, startDate, endDate);
    }
}
