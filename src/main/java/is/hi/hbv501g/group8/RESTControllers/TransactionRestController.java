/**
 *  Transaction REST Controller
 */

package is.hi.hbv501g.group8.RESTControllers;

import is.hi.hbv501g.group8.Persistence.Entities.*;
import is.hi.hbv501g.group8.Services.EmployeeService;
import is.hi.hbv501g.group8.Services.TransactionReviewService;
import is.hi.hbv501g.group8.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("G'day! " + temp.getFirstName());
        }

        exists.setClockOut(LocalDateTime.now());
        exists.setStatus("pending");
        exists.setFinished(true);
        transactionService.save(exists);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("G'bye! " + temp.getFirstName());
    }

    /**
     * TEMPORARY Handler for GET requests on /list
     *
     *  Returns transaction for active user spanning from date 1 to date 2.
     *  By default, it's the current month.
     */
    @RequestMapping(value="/list", method = RequestMethod.PUT)
    public ResponseEntity<ViewTransactionUserDAO> tempDisplayTransactions(@RequestBody TransHelper transHelper) {
        ViewTransactionUserDAO reval = new ViewTransactionUserDAO();

        // Date Configuration
        if(transHelper.getDateFrom() == null || transHelper.getDateTo() == null) {
            LocalDate shortDate = LocalDate.now();
            transHelper.setDateFrom(LocalDate.of(shortDate.getYear(), shortDate.getMonth(), 1));
            transHelper.setDateTo(transHelper.getDateFrom().plusMonths(1));
        }

        System.out.println("Debug: SSN: " + transHelper.getSsn() + "Date:"
                + transHelper.getDateFrom().toString() + " to "
                + transHelper.getDateTo().toString());

        // Transaction Data
        List<Transaction> transactionList = transactionService.findAllBySSNAndClockInBetween(
                transHelper.getSsn(),
                transHelper.getDateFrom().atStartOfDay(),
                transHelper.getDateTo().atStartOfDay()
        );
        double revalTotal = 0;
        for (Transaction tmp : transactionList) {
            System.out.println(tmp.getSSN());
            revalTotal += tmp.getDuration() / 60.0;
        }
        // Configure DAO
        SessionUser reSess = new SessionUser();
        reSess.setSsn(transHelper.getSsn());
        reval.setTransactionList(transactionList);
        reval.setTotal_hours(revalTotal);
        reval.setSessionUser(reSess);
        return ResponseEntity.ok().body(reval);
    }
}
