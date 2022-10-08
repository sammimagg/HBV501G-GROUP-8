package is.hi.hbv501g.group8.Services;

import is.hi.hbv501g.group8.Persistence.Entities.TransactionReview;

import java.util.List;

public interface TransactionReviewService {
    TransactionReview save(TransactionReview transactionReview);
    void delete(TransactionReview transactionReview);

    List<TransactionReview> findAll();
    List<TransactionReview> findBySSN(String ssn);
    List<TransactionReview> findAllByStatus(String status); // Find unfinished eða finished
    // Either finding all review requests that are in need
    // of reviewing, or a log of all reviewed requests
    TransactionReview findByID(long id);
}
