/**
 * Transaction Controller
 *
 * Description: Controller for Transactions
 *              Each transaction represents a clock-in and a clock-out
 *
 * @author kristófer Breki Gylfason - kbg15@hi.is
 * @author Halldór Jens Vilhjálsson - hjv6@hi.is
 * @author Samúel Magnússon - sam38@hi.is
 */
package is.hi.hbv501g.group8.Controllers;

import is.hi.hbv501g.group8.Persistence.Entities.*;
import is.hi.hbv501g.group8.Persistence.Repositories.UserRepository;
import is.hi.hbv501g.group8.Services.EmployeeService;
import is.hi.hbv501g.group8.Services.TransactionReviewService;
import is.hi.hbv501g.group8.Services.TransactionService;
import is.hi.hbv501g.group8.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class TransactionController {

    TransactionService transactionService;
    EmployeeService employeeService;
    TransactionReviewService transactionReviewService;

    LocalDate dateOne;
    LocalDate dateTwo;

    /**
     * {@link java.lang.reflect.Constructor} for TransactionController
     *
     * @param transactionService
     * @param employeeService
     * @param transactionReviewService
     */
    @Autowired
    public TransactionController(TransactionService transactionService, EmployeeService employeeService, TransactionReviewService transactionReviewService) {
        this.transactionService = transactionService;
        this.employeeService = employeeService;
        this.transactionReviewService = transactionReviewService;
    }

    /**
     * Handler for GET requests on /
     *
     * Displays the default clock-in page. No authentication required
     *
     * @param transaction Transaction
     * @return clock A view for clocking in
     */
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String transactionGET(Transaction transaction) {
        return "clock";
    }



    /**
     * Handler for POST requests on /
     *
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
     * @param result BindingResult
     * @param model Model
     * @return redirect /
     */
    @RequestMapping(value="/", method = RequestMethod.POST)
    public String transactionPOST(Transaction transaction, RedirectAttributes redirectAttributes, BindingResult result, Model model){
        if(result.hasErrors()) {

            return "redirect:/";
        }
        Transaction exists = transactionService.findBySSNAndFinished(transaction.getSSN(), false);
        Employee temp = employeeService.findBySSN(transaction.getSSN());
        if(temp == null) {
            redirectAttributes.addAttribute("message", "SSN not registered");
            return "redirect:/";
        }
        if(exists == null) {
            transaction.setClockIn(LocalDateTime.now());
            transaction.setFinished(false);
            transactionService.save(transaction);
            redirectAttributes.addAttribute("message", "Welcome, "+temp.getFirstName()+"!");
            return "redirect:/";
        }
        else {
            exists.setClockOut(LocalDateTime.now());
            exists.setStatus("pending");
            exists.setFinished(true);
            redirectAttributes.addAttribute("message", "Thank you have. Have a nice day, "+temp.getFirstName()+"!");
            transactionService.save(exists);
        }
        return "redirect:/";
    }
    /**
     * Handler for GET requests on /list
     *
     * Returns a page with input fields for seeing transactions
     *
     * @param model Model
     * @param dateHelper DateHelper
     * @param session HttpSession
     * @param user User
     * @return listview a view for vlistview
     */
    @RequestMapping(value="/list", method = RequestMethod.GET)
    public String transactionsGET(Model model, DateHelper dateHelper, HttpSession session, User user) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if(sessionUser == null) return "redirect:/";
        if (sessionUser!=null) {
            model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
            model.addAttribute("abbreviation",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName().charAt(0) + "" + employeeService.findBySSN(sessionUser.getSSN()).getLastName().charAt(0)));
            model.addAttribute("fullName",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName() + " " + employeeService.findBySSN(sessionUser.getSSN()).getLastName()));
            model.addAttribute("userRole",sessionUser.getAccounttype()); // Used to display the right nav bar
            model.addAttribute("activePage", "timeAndAttendance");
        }
        System.out.println(dateHelper.getDate1());
        dateOne = dateHelper.getDate1();
        dateTwo = dateHelper.getDate2();

        long totalHours = 0;

        LocalDate currentDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);

        for (Transaction row : transactionService.findAllBySSNAndClockInBetween(sessionUser.getSSN(), currentDate.atStartOfDay(), currentDate.plusMonths(1).atStartOfDay())) {
            totalHours += row.getDuration();
        }

        double ttlHrs = totalHours / 60.0;

        model.addAttribute("transactions", getTransactionList(sessionUser.getSSN(),currentDate, currentDate.plusMonths(1)));
        model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
        model.addAttribute("status","approved"); // Setur status merki á Transaction listan.
        model.addAttribute("totalHours", ttlHrs);

        return "listview";
    }

    /**
     * Handler for POST requests on /list
     *
     * Uses authenticated user, inputted dates to find relevant transaction history
     *
     * @param model Model
     * @param user User
     * @param dateHelper DateHelper
     * @param session HttpSession
     * @return listview A view for listview
     */
    @RequestMapping(value="/list", method = RequestMethod.POST)
    public String TransactionsPOST(Model model, User user, DateHelper dateHelper, HttpSession session) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");

        if (sessionUser == null ) {
            return "redirect:/login";
        }
        else {
            model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
            model.addAttribute("abbreviation",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName().charAt(0) + "" + employeeService.findBySSN(sessionUser.getSSN()).getLastName().charAt(0)));
            model.addAttribute("fullName",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName() + " " + employeeService.findBySSN(sessionUser.getSSN()).getLastName()));
            model.addAttribute("userRole",sessionUser.getAccounttype()); // Used to display the right nav bar
            model.addAttribute("activePage", "timeAndAttendance");
        }

        dateOne = dateHelper.getDate1();
        dateTwo = dateHelper.getDate2();
        System.out.println(dateOne + "hér");

        long totalHours = 0;

        for (Transaction row : transactionService.findAllBySSNAndClockInBetween(sessionUser.getSSN(), dateHelper.getDate1().atStartOfDay(), dateHelper.getDate2().atStartOfDay())) {
            totalHours += row.getDuration();
        }

        double ttlHrs = totalHours / 60.0;


        model.addAttribute("transactions", getTransactionList(sessionUser.getSSN(),dateHelper.getDate1(),dateHelper.getDate2()));
        model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
        model.addAttribute("status","approved"); // Setur status merki á Transaction listan.
        model.addAttribute("totalHours", ttlHrs);
        return "listview";
    }
    /**
     * Handler for POST requests on /edit
     *
     * Submits a Request-Review
     *
     * @param model Model
     * @param user User
     * @param rr RequestReview
     * @param dateHelper DateHelper
     * @param session HttpSession
     * @return listview A view for listview
     */
    @RequestMapping(value="/edit", method = RequestMethod.POST)
    public String edit(Model model, User user, RequestReview rr,DateHelper dateHelper, HttpSession session){
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser!=null) {
            model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
            model.addAttribute("abbreviation",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName().charAt(0) + "" + employeeService.findBySSN(sessionUser.getSSN()).getLastName().charAt(0)));
            model.addAttribute("fullName",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName() + " " + employeeService.findBySSN(sessionUser.getSSN()).getLastName()));
            model.addAttribute("userRole",sessionUser.getAccounttype()); // Used to display the right nav bar
        }
        model.addAttribute("activePage", "timeAndAttendace");
        //model.addAttribute("transactions", getTransactionList(sessionUser.getSSN(),dateHelper.getDate1(),dateHelper.getDate2()));
       System.out.println("Date: " + rr.getDate() + " TimeIn: " + rr.getTimeIn() + " TimeOut: " + rr.getTimeOut() +" ID: " + rr.getId());
       System.out.println("Date1 " + dateOne + " Date2 " + dateTwo);


        Transaction orginalTransaction =transactionService.findByID(Long.parseLong(rr.getId()));
        orginalTransaction.setStatus("request");

        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter TIMEFORMATTER = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate dateFrom = LocalDate.parse(rr.getDate(),DATEFORMATTER);
        LocalTime timeFrom = LocalTime.parse(rr.getTimeIn(),TIMEFORMATTER);
        LocalDate dateTo = LocalDate.parse(rr.getDate(),DATEFORMATTER);
        LocalTime timeTo = LocalTime.parse(rr.getTimeOut(),TIMEFORMATTER);

        LocalDateTime dateTimeFrom = LocalDateTime.of(dateFrom,timeFrom);
        LocalDateTime dateTimeTo = LocalDateTime.of(dateTo,timeTo);

        TransactionReview exists = transactionReviewService.findByID(Long.parseLong(rr.getId()));
        if(exists == null){
            TransactionReview newTransaction = new TransactionReview(Long.parseLong(rr.getId()),
                    user.getSSN(),
                    "pending",
                    dateTimeFrom,
                    dateTimeTo);
            newTransaction.setOriginalClockIn(orginalTransaction.getClockIn());
            newTransaction.setOriginalClockOut(orginalTransaction.getClockOut());
            newTransaction.setSSN(orginalTransaction.getSSN());
            transactionReviewService.save(newTransaction);
        }
        else {
            exists.setStatus("pending");
            exists.setChangedClockIn(dateTimeFrom);
            exists.setChangedClockOut(dateTimeTo);
            exists.setSSN(orginalTransaction.getSSN());
            transactionReviewService.save(exists);
        }

        transactionService.save(orginalTransaction);

        return "redirect:/list";
    }
    /**
     * Handler for POST requests on /edit
     *
     * Preperation for Request Review
     *
     * @param model Model
     * @param user User
     * @param rr RequestReview
     * @param dateHelper DateHelper
     * @param session HttpSession
     * @return listview A view for listview
     */
    @RequestMapping(value="/edit", method = RequestMethod.GET)
    public String geteditTransaction(Model model, User user,DateHelper dateHelper, RequestReview rr, HttpSession session){
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser!=null) {
            model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
            model.addAttribute("abbreviation",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName().charAt(0) + "" + employeeService.findBySSN(sessionUser.getSSN()).getLastName().charAt(0)));
            model.addAttribute("fullName",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName() + " " + employeeService.findBySSN(sessionUser.getSSN()).getLastName()));
            model.addAttribute("userRole",sessionUser.getAccounttype()); // Used to display the right nav bar
            model.addAttribute("activePage", "timeAndAttendance");
        }



        model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
        //model.addAttribute("status","approved"); // Setur status merki á Transaction listan.

        System.out.println("Date: " + rr.getDate() + " TimeIn: " + rr.getTimeIn() + " TimeOut: " + rr.getTimeOut() +" ID: " + rr.getId());
        return "listview";
    }
    /**
     * Generates list for user by SSN, Date from, and Date to.
     *
     * @param SSN The SSN of the user who is searching
     * @see Transaction
     * @param dateFrom Search transaction from date
     * @param dateTo Search transaction to date
     * @return List<Transaction>
     */
    public List<Transaction> getTransactionList(String SSN,LocalDate dateFrom, LocalDate dateTo){
        List<Transaction> allTransactions;

        if(dateFrom != null && dateTo != null) {
             allTransactions = transactionService.findAllBySSNAndClockInBetween(SSN, dateFrom.atStartOfDay(), dateTo.atStartOfDay());
            for ( Transaction row : allTransactions) {
                row.setClockInDate(row.getClockIn().toLocalDate());
                row.setClockInTime(LocalTime.from(row.getClockIn()).truncatedTo(ChronoUnit.MINUTES));
                if(row.isFinished() == true) {
                    row.setWorkedHours(row.getClockOut().toLocalTime().getHour() - row.getClockIn().toLocalTime().getHour());
                    row.setWorkedMinutes(row.getClockOut().toLocalTime().getMinute() - row.getClockIn().toLocalTime().getMinute());
                }
                else {
                    row.setWorkedHours(LocalTime.now().getHour()- row.getClockIn().toLocalTime().getHour());
                    row.setWorkedMinutes(LocalTime.now().getMinute()- row.getClockIn().toLocalTime().getMinute());
                }
                if(row.getClockOut() != null) {
                    row.setClockOutTime(LocalTime.from(row.getClockOut()).truncatedTo(ChronoUnit.MINUTES));
                }
            }
        }
        else {
            allTransactions = transactionService.findAllBySSNAndClockInBetween(SSN, dateOne.atStartOfDay(), dateTwo.atStartOfDay());
            for ( Transaction row : allTransactions) {
                row.setClockInDate(row.getClockIn().toLocalDate());
                row.setClockInTime(LocalTime.from(row.getClockIn()).truncatedTo(ChronoUnit.MINUTES));
                if(row.getClockOut() != null) {
                    row.setClockOutTime(LocalTime.from(row.getClockOut()).truncatedTo(ChronoUnit.MINUTES));
                }
            }
        }
        return allTransactions;
    }
}