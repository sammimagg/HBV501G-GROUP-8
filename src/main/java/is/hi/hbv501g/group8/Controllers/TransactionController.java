package is.hi.hbv501g.group8.Controllers;

import is.hi.hbv501g.group8.Persistence.Entities.Transaction;
import is.hi.hbv501g.group8.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;

@Controller
public class TransactionController {

    TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
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
    public String transactionPOST(Transaction transaction, BindingResult result, Model model){
        if(result.hasErrors()){
            return "redirect:/";
        }
        Transaction exists = transactionService.findBySSNAndFinished(transaction.getSSN(), false);
        if(exists == null){
            transaction.setClockIn(LocalDateTime.now());
            transaction.setFinished(false);
            transactionService.save(transaction);
        } else {
            exists.setClockOut(LocalDateTime.now());
            exists.setFinished(true);
            transactionService.save(exists);
        }

        return "redirect:/";
    }

}