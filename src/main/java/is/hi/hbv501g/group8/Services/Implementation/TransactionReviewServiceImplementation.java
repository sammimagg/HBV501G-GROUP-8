package is.hi.hbv501g.group8.Services.Implementation;


import is.hi.hbv501g.group8.Persistence.Entities.TransactionReview;
import is.hi.hbv501g.group8.Persistence.Repositories.TransactionReviewRepository;
import is.hi.hbv501g.group8.Services.TransactionReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionReviewServiceImplementation implements TransactionReviewService {
    private final TransactionReviewRepository transactionReviewRepository;

    @Autowired
    public TransactionReviewServiceImplementation(TransactionReviewRepository transactionReviewRepository) {
        this.transactionReviewRepository = transactionReviewRepository;
    }

    /**
     * @param transactionReview
     * @return
     */
    @Override
    public TransactionReview save(TransactionReview transactionReview) {
        return transactionReviewRepository.save(transactionReview);
    }

    /**
     * @param transactionReview
     */
    @Override
    public void delete(TransactionReview transactionReview) {
        transactionReviewRepository.delete(transactionReview);
    }

    /**
     * @return
     */
    @Override
    public List<TransactionReview> findAll() {
        return transactionReviewRepository.findAll();
    }

    /**
     * @param ssn
     * @return
     */
    @Override
    public List<TransactionReview> findBySSN(String ssn) {
        return transactionReviewRepository.findBySSN(ssn);
    }

    /**
     * @param status
     * @return
     */
    @Override
    public List<TransactionReview> findAllByStatus(String status) {
        return transactionReviewRepository.findAllByStatus(status);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public TransactionReview findByID(long id) {
        return transactionReviewRepository.findByID(id);
    }
}