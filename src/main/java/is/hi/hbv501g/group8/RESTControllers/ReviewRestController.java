/**
 * Review REST Controller
 *
 * RESTful API controller for managing Review Requests.
 * Provides endpoints for creating new Review requests, reviewing and approving/rejecting the requests,
 * and displaying status information about the requests.
 *
 * @TODO 12. Mars 2023
 *      - Implement:
 *          - GET requests
 *          - Accept/Decline requests
 *          - ????
 *
 *
 * @author Halldór Jens Vilhjálsson - hjv6@hi.is
 * @author Kristófer Breki Gylfason - kbg15@hi.is
 * @author Samúel Magnússon - sam38@hi.is
 * @version 1.0
 * @since 2023-03-11
 */

package is.hi.hbv501g.group8.RESTControllers;

import is.hi.hbv501g.group8.Persistence.Entities.Jesus;
import is.hi.hbv501g.group8.Persistence.Entities.MappedRequestUserDAO;
import is.hi.hbv501g.group8.Persistence.Entities.Transaction;
import is.hi.hbv501g.group8.Persistence.Entities.TransactionReview;
import is.hi.hbv501g.group8.Services.EmployeeService;
import is.hi.hbv501g.group8.Services.TransactionReviewService;
import is.hi.hbv501g.group8.Services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewRestController {

    private TransactionReviewService transactionReviewService;
    private TransactionService transactionService;
    private EmployeeService employeeService;

    public ReviewRestController(TransactionReviewService transactionReviewService, TransactionService transactionService, EmployeeService employeeService) {
        this.transactionReviewService = transactionReviewService;
        this.transactionService = transactionService;
        this.employeeService = employeeService;
    }

    /**
     * Handler for fetching all pending Review Requests
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ArrayList<MappedRequestUserDAO> fetchReviews() {
        List<TransactionReview> allReviewRequests = transactionReviewService.findAllByStatus("pending");
        ArrayList<MappedRequestUserDAO> reval = new ArrayList<>();

        for (TransactionReview transactionReview : allReviewRequests) {
            reval.add(new MappedRequestUserDAO(
                    transactionReview,
                    employeeService.findBySSN(transactionReview.getSSN())
            ));
        }

        return reval;
    }

    /**
     * Handler for creating a new Review Request
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<?> newReview(@RequestBody TransactionReview transactionReview){
        Transaction originalTransaction = transactionService.findByID(transactionReview.getID());
        if (originalTransaction == null) return ResponseEntity.badRequest().body("That's unexpected innit?");
        transactionReview.setStatus("pending");
        transactionReview.setOriginalClockIn(originalTransaction.getClockIn());
        transactionReview.setOriginalClockOut(originalTransaction.getClockOut());
        originalTransaction.setStatus("request");

        transactionReviewService.save(transactionReview);
        transactionService.save(originalTransaction);

        return ResponseEntity.ok().body("Successful request!");
    }

    /**
     * Handler for accepting or rejecting an Edit Review Request.
     *
     * @TODO Currently anyone can approve/reject requests. Fix
     *
     * @param requestId The ID of the Review Request being accepted or rejected.
     * @param approved  A boolean indicating whether the Edit Review Request is approved or not.
     * @return HTTP response code and message indicating the success or failure of the request.
     */
    @RequestMapping(value = "/{requestId}", method = RequestMethod.POST)
    public ResponseEntity<String> respondToRequest(@PathVariable long requestId, @RequestBody Jesus jsonHlutur) {
        TransactionReview transactionReview = transactionReviewService.findByID(requestId);
        Transaction connectedTransaction = transactionService.findByID(requestId);
        Boolean approved = jsonHlutur.getApproved();
        String transactionStatus = approved ? "approved" : "rejected";

        if (transactionReview == null || !transactionReview.getStatus().equals("pending")) {
            return ResponseEntity.badRequest().body("Invalid or processed request ID.");
        }

        if (approved) {
            connectedTransaction.setClockIn(transactionReview.getChangedClockIn());
            connectedTransaction.setClockOut(transactionReview.getChangedClockOut());
        }

        // Transaction Review Entity
        transactionReview.setStatus(transactionStatus);
        transactionReviewService.save(transactionReview);

        // Transaction Entity
        connectedTransaction.setStatus("request " + transactionStatus);
        transactionService.save(connectedTransaction);


        return ResponseEntity.ok("Review request has been: " + transactionStatus);
    }

}
