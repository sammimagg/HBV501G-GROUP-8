/**
 * Transaction Controller
 *
 * Description:
 *
 * @author kristófer Breki Gylfason - kbg15@hi.is
 * @author Halldór Jens Vilhjálsson - hjv6@hi.is
 * @author Samúel Magnússon - sam38@hi.is
 */
package is.hi.hbv501g.group8.Controllers;

import is.hi.hbv501g.group8.Persistence.Entities.DateHelper;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class TransactionController {

    TransactionService transactionService;
    EmployeeService employeeService;

    /**
     * Vantar lýsingu..
     *
     * @param transactionService
     * @param employeeService
     */
    @Autowired
    public TransactionController(TransactionService transactionService, EmployeeService employeeService) {
        this.transactionService = transactionService;
        this.employeeService = employeeService;
    }

    /**
     * Vantar lýsingu..
     *
     * @param transaction
     * @return
     */
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
        if(result.hasErrors()) {

            return "redirect:/";
        }
        Transaction exists = transactionService.findBySSNAndFinished(transaction.getSSN(), false);
        Employee temp = employeeService.findBySSN(transaction.getSSN());
        if(temp == null) {
            redirectAttributes.addAttribute("message", "Þú vinna ekki hafa");
            return "redirect:/";
        }
        if(exists == null) {
            transaction.setClockIn(LocalDateTime.now());
            transaction.setFinished(false);
            transactionService.save(transaction);
            redirectAttributes.addAttribute("message", "Velkomin/n, "+temp.getFirstName()+"!");
            return "redirect:/";
        }
        else {
            exists.setClockOut(LocalDateTime.now());
            exists.setFinished(true);
            redirectAttributes.addAttribute("message", "Takk fyrir daginn, "+temp.getFirstName()+"!");
            transactionService.save(exists);
        }
        return "redirect:/";
    }
    /**
     * Vantar lýsingu..
     *
     * @param model
     * @param dateHelper
     * @param session
     * @param user
     * @return listview
     */
    @RequestMapping(value="/list", method = RequestMethod.GET)
    public String transactionsGET(Model model, DateHelper dateHelper, HttpSession session, User user) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser!=null) {
            model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
        }
        return "listview";
    }

    /**
     * Vantar lýsingu..
     *
     * @param model
     * @param user
     * @param dateHelper
     * @param session
     * @return listview
     */
    @RequestMapping(value="/list", method = RequestMethod.POST)
    public String TransactionsPOST(Model model, User user, DateHelper dateHelper, HttpSession session) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser == null ) return "redirect:/login";
        System.out.println(dateHelper.getDate1());
        List<Transaction> allTransactions = transactionService.findAllBySSNAndClockInBetween(sessionUser.getSSN(), dateHelper.getDate1().atStartOfDay(), dateHelper.getDate2().atStartOfDay());
        System.out.println(sessionUser.getSSN());
        for ( Transaction row : allTransactions) {
            System.out.println("22");
        }
        model.addAttribute("transactions", allTransactions);
        model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
        return "listview";
    }

    /**
     * Vantar lýsingu..
     *
     * @param id
     * @param model
     * @return redirect:/list
     */
    @RequestMapping(value="/request-review/{id}", method = RequestMethod.GET)
    public String requestReview(@PathVariable("id") long id, Model model){
        Transaction transactionToMarkForReview = transactionService.findByID(id);
        //bookService.delete(bookToDelete);
        return "redirect:/list";
    }
    // <td><a th:href="@{/request-review/{id}(id=${transaction.ID})}">Delete</a></td>

}