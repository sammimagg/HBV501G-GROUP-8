/*
*
 */

package is.hi.hbv501g.group8.Persistence.Repositories;

import is.hi.hbv501g.group8.Persistence.Entities.TransactionReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionReviewRepository extends JpaRepository<TransactionReview, Long> {
    TransactionReview save(TransactionReview transactionReview);
    void delete(TransactionReview transactionReview);

    List<TransactionReview> findAll();
    List<TransactionReview> findBySSN(String ssn);
    List<TransactionReview> findAllByStatus(String status); // Find unfinished eða finished
                                                            // Either finding all review requests that are in need
                                                            // of reviewing, or a log of all reviewed requests
    TransactionReview findByID(long id);
}
