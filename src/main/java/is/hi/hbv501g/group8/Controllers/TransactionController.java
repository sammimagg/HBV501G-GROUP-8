package is.hi.hbv501g.group8.Controllers;

import is.hi.hbv501g.group8.Persistence.Entities.Employee;
import is.hi.hbv501g.group8.Persistence.Entities.Transaction;
import is.hi.hbv501g.group8.Persistence.Entities.User;
import is.hi.hbv501g.group8.Persistence.Repositories.UserRepository;
import is.hi.hbv501g.group8.Services.EmployeeService;
import is.hi.hbv501g.group8.Services.TransactionService;
import is.hi.hbv501g.group8.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class TransactionController {

    TransactionService transactionService;
    EmployeeService employeeService;

    @Autowired
    public TransactionController(TransactionService transactionService, EmployeeService employeeService) {
        this.transactionService = transactionService;
        this.employeeService = employeeService;
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String transactionGET(Transaction transaction) {
        return "clock";
    }


    /**
     *  Function searches for a transaction in the databased by SSN and Finished.
     *  transactions marked unfinished with finished: false flag mean that the user has
     *  not clocked out.
     *
     *  If a open transaction is found, that transaction gets updated with:
     *  [finished: true, clocked_out: current LocalDateTime]
     *
     *  If no open transaction is found, a new one is created with current LocalDateTime
     *  and unfinished flag. (finished:false)
     *
     * @param transaction Transaction object.
     * @see Transaction
     * @param result ónotað
     * @param model ónotað
     * @return redirect
     */
    @RequestMapping(value="/", method = RequestMethod.POST)
    public String transactionPOST(Transaction transaction, RedirectAttributes redirectAttributes, BindingResult result, Model model){
        if(result.hasErrors()){
            return "redirect:/";
        }
        Transaction exists = transactionService.findBySSNAndFinished(transaction.getSSN(), false);
        Employee temp = employeeService.findBySSN(transaction.getSSN());
        if(temp == null) {
            redirectAttributes.addAttribute("message", "Þú vinna ekki hafa");
            return "redirect:/";
        }
        if(exists == null){
            transaction.setClockIn(LocalDateTime.now());
            transaction.setFinished(false);
            transactionService.save(transaction);
            redirectAttributes.addAttribute("message", "Velkomin/n, "+temp.getFirstName()+"!");
            return "redirect:/";
        } else {
            exists.setClockOut(LocalDateTime.now());
            exists.setFinished(true);
            redirectAttributes.addAttribute("message", "Takk fyrir daginn, "+temp.getFirstName()+"!");
            transactionService.save(exists);
        }

        return "redirect:/";
    }

}