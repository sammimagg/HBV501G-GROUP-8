/**
 *  Transaction REST Controller
 */

package is.hi.hbv501g.group8.RESTControllers;

import is.hi.hbv501g.group8.Persistence.Entities.Employee;
import is.hi.hbv501g.group8.Persistence.Entities.SessionUser;
import is.hi.hbv501g.group8.Persistence.Entities.Transaction;
import is.hi.hbv501g.group8.Persistence.Entities.ViewTransactionUserDAO;
import is.hi.hbv501g.group8.Services.EmployeeService;
import is.hi.hbv501g.group8.Services.TransactionReviewService;
import is.hi.hbv501g.group8.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionRestController {
    TransactionService transactionService;
    EmployeeService employeeService;
    TransactionReviewService transactionReviewService;

    @Autowired
    public TransactionRestController(TransactionService transactionService, EmployeeService employeeService, TransactionReviewService transactionReviewService) {
        this.transactionService = transactionService;
        this.employeeService = employeeService;
        this.transactionReviewService = transactionReviewService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> clockIn(@RequestBody SessionUser sessionUser){
        Transaction exists = transactionService.findBySSNAndFinished(sessionUser.getSsn(), false);
        Employee temp = employeeService.findBySSN(sessionUser.getSsn());
        System.out.println("test");
        if(temp == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SSN is not registered!");
        }

        if(exists == null) {
            Transaction transaction = new Transaction();
            transaction.setSSN(sessionUser.getSsn());
            transaction.setClockIn(LocalDateTime.now());
            transaction.setFinished(false);
            transactionService.save(transaction);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(temp.getFirstName());
        }

        exists.setClockOut(LocalDateTime.now());
        exists.setStatus("pending");
        exists.setFinished(true);
        transactionService.save(exists);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(temp.getFirstName());
    }

    /**
     * Handler for GET requests on /list
     *
     *  Returns transaction for active user spanning from date 1 to date 2.
     *  By default, it's the current month.
     */
    @GetMapping(value = "/list")
    public ViewTransactionUserDAO displayTransactions() {
        ViewTransactionUserDAO reval = new ViewTransactionUserDAO();

        return reval;
    }

    /**
     * TEMPORARY Handler for GET requests on /list
     *
     *  Returns transaction for active user spanning from date 1 to date 2.
     *  By default, it's the current month.
     */
    @GetMapping(value = "/list/{ssn}")
    public ViewTransactionUserDAO tempDisplayTransactions(@PathVariable("ssn") String ssn) {
        ViewTransactionUserDAO reval = new ViewTransactionUserDAO();

        // Date Configuration
        LocalDateTime shortDate = LocalDateTime.now();
        LocalDateTime dateFrom = LocalDateTime.of(shortDate.getYear(), shortDate.getMonth(), 1, 0, 0);
        LocalDateTime dateTo = dateFrom.plusMonths(1);

        // Transaction Data
        List<Transaction> transactionList = transactionService.findAllBySSNAndClockInBetween(ssn, dateFrom, dateTo);
        double revalTotal = 0;
        for (Transaction tmp : transactionList) {
            revalTotal += tmp.getDuration() / 60.0;
        }
        // Configure DAO
        reval.setTransactionList(transactionList);
        reval.setTotal_hours(revalTotal);
        reval.setUser(employeeService.findBySSN(ssn)); // Replace w/ JWT / Session User

        return reval;
    }
}
