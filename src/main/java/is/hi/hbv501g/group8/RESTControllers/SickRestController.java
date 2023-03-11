package is.hi.hbv501g.group8.RESTControllers;

import is.hi.hbv501g.group8.Persistence.Entities.Deviation;
import is.hi.hbv501g.group8.Persistence.Entities.Employee;
import is.hi.hbv501g.group8.Persistence.Entities.Transaction;
import is.hi.hbv501g.group8.Persistence.Entities.User;
import is.hi.hbv501g.group8.Services.EmployeeService;
import is.hi.hbv501g.group8.Services.TransactionReviewService;
import is.hi.hbv501g.group8.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping(value = "api/sick")
public class SickRestController {
    private final TransactionReviewService transactionReviewService;
    private final EmployeeService employeeService;
    private final EmployeeService.DeviationService deviationService;
    private final TransactionService transactionService;

    @Autowired
    public SickRestController(TransactionReviewService transactionReviewService, EmployeeService employeeService, EmployeeService.DeviationService deviationService, TransactionService transactionService) {
        this.transactionReviewService = transactionReviewService;
        this.employeeService = employeeService;
        this.deviationService = deviationService;
        this.transactionService = transactionService;
    }

    /**
     */
    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity<?> postSickAndVacation(@RequestBody Deviation deviation, User user){
        deviation.setSSN(user.getSSN());
        deviationService.save(deviation);

        Employee emp = employeeService.findBySSN(user.getSSN());
        int currDays;

        long daysBetween = ChronoUnit.DAYS.between(deviation.getDateFrom(), deviation.getDateTo());
        System.out.println(daysBetween);
        System.out.println((int) daysBetween);
        System.out.println(deviation.getType() == Deviation.TYPE.SICK);
        if(deviation.getType() == Deviation.TYPE.SICK) {
            currDays = emp.getSickDaysUsed();
            emp.setSickDaysUsed(currDays + (int) daysBetween);
            employeeService.save(emp);

        } else if (deviation.getType() == Deviation.TYPE.VACATION) {
            currDays = emp.getVacationDaysUsed();
            emp.setVacationDaysUsed(currDays + (int) daysBetween);
            employeeService.save(emp);
        }

        /*
        @TODO: Configure to work with shift schedule!

        Currently:
        Creates a new EMPTY (00:00 - 00:00) transaction for each day sick/vacation, pending
        manual alteration from employee with relevant hours
         */

        for (int i = 0; i < (int) daysBetween; i++){
            LocalDate newDate = deviation.getDateFrom().plusDays(i);

            Transaction newTransaction = new Transaction();
            newTransaction.setSSN(emp.getSSN());
            newTransaction.setFinished(true);
            newTransaction.setStatus("pending"+" "+deviation.getType().toString());
            newTransaction.setClockIn(newDate.atStartOfDay());
            newTransaction.setClockOut(newDate.atStartOfDay());

            transactionService.save(newTransaction);
        }


        return ResponseEntity.ok().build();
    }

}
