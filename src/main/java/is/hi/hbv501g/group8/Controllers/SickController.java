/**
 * Sick Controller
 *
 * Description: Controller for everything to do with
 *              sick- and vacation days. Maybe other
 *              unforeseen exceptions.
 *
 * @// TODO: 22.10.2022
 *
 * @author kristófer Breki Gylfason - kbg15@hi.is
 * @author Halldór Jens Vilhjálsson - hjv6@hi.is
 * @author Samúel Magnússon - sam38@hi.is
 */

package is.hi.hbv501g.group8.Controllers;

import is.hi.hbv501g.group8.Persistence.Entities.*;
import is.hi.hbv501g.group8.Services.DeviationService;
import is.hi.hbv501g.group8.Services.EmployeeService;
import is.hi.hbv501g.group8.Services.TransactionReviewService;
import is.hi.hbv501g.group8.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Controller
public class SickController {
    private final TransactionReviewService transactionReviewService;
    private final EmployeeService employeeService;
    private final DeviationService deviationService;
    private final TransactionService transactionService;

    @Autowired
    public SickController(TransactionReviewService transactionReviewService, EmployeeService employeeService, DeviationService deviationService, TransactionService transactionService) {
        this.transactionReviewService = transactionReviewService;
        this.employeeService = employeeService;
        this.deviationService = deviationService;
        this.transactionService = transactionService;
    }

    @RequestMapping(value="/sickandvacation", method = RequestMethod.GET)
    public String getSickAndVacation(Model model, User user, HttpSession session, Deviation deviation){
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser!=null) {
            model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
            model.addAttribute("abbreviation",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName().charAt(0) + "" + employeeService.findBySSN(sessionUser.getSSN()).getLastName().charAt(0)));
            model.addAttribute("fullName",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName() + " " + employeeService.findBySSN(sessionUser.getSSN()).getLastName()));
            model.addAttribute("userRole",sessionUser.getAccounttype()); // Used to display the right nav bar
        }
        return "sickandvacation";
    }


    @RequestMapping(value="/sickandvacation", method = RequestMethod.POST)
    public String postSickAndVacation(Model model, User user, HttpSession session, Deviation deviation){
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser!=null) {
            model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
            model.addAttribute("abbreviation",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName().charAt(0) + "" + employeeService.findBySSN(sessionUser.getSSN()).getLastName().charAt(0)));
            model.addAttribute("fullName",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName() + " " + employeeService.findBySSN(sessionUser.getSSN()).getLastName()));
            model.addAttribute("userRole",sessionUser.getAccounttype()); // Used to display the right nav bar
        }

        if (sessionUser == null ) {
            return "redirect:/login";
        }

        deviation.setSSN(sessionUser.getSSN());
        deviationService.save(deviation);

        /*
        *    Væri flottara ef þetta væri sitt eigið fall
        *    En við erum að breyta used Sick/Vacation days hér
        */

        Employee emp = employeeService.findBySSN(sessionUser.getSSN());
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


        return "sickandvacation";
    }

    /*
     * Tafla sem heldur utan um veikindi/frí
     * Ný veikindaskráning =>
     *  A. Ný transaction fyrir hvern dag í veikindum. Innstimplun OG útstimplun 08:00 = 0 mín en hægt að breyta m/ review
     *      A1. Status = Sick-Unreviewd
     *  B. Hækka sick_days_used í employees töflunni
     *
     *
     */
}