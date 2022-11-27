/**
 * Driving Log Controller
 *
 * Description: Controller for maintaining a driving log for employees 
 *
 *
 * @author Kristófer Breki Gylfason - kbg15@hi.is
 * @author Halldór Jens Vilhjálsson - hjv6@hi.is
 * @author Samúel Magnússon - sam38@hi.is
 */

package is.hi.hbv501g.group8.Controllers;

import is.hi.hbv501g.group8.Persistence.Entities.DateHelper;
import is.hi.hbv501g.group8.Persistence.Entities.Driving;
import is.hi.hbv501g.group8.Persistence.Entities.Transaction;
import is.hi.hbv501g.group8.Persistence.Entities.User;
import is.hi.hbv501g.group8.Services.DrivingService;
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
import java.util.Date;
import java.util.List;

@Controller
public class DrivingLogController {

    private DrivingService drivingService;
    private EmployeeService employeeService;

    @Autowired
    public DrivingLogController(DrivingService drivingService, EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.drivingService = drivingService;
    }

    /**
     * Handler for GET requests on /drivinglog
     *
     * Displays the driving log for the logged in user, for the current tímabil.
     *
     * @param driving Driving
     * @param user User
     * @param session HttpSession
     * @param model Model
     * @param dateHelper DateHelper
     * @return clock A view for clocking in
     */
    @RequestMapping(value="/drivinglog", method = RequestMethod.GET)
    public String drivingGET(Driving driving, User user, HttpSession session, Model model, DateHelper dateHelper) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser == null ) {
            return "redirect:/login";
        }
        loadUserComponent(sessionUser, model);
        model.addAttribute("activePage", "drivingLog");

        LocalDate currentDate = LocalDate.of(
                LocalDate.now().getYear(),
                LocalDate.now().getMonth(),
                1
        );

        List<Driving> defaultLog = drivingService.findAllBySSNAndDagsBetween(
                sessionUser.getSSN(),
                currentDate, currentDate.plusMonths(1)
        );
        model.addAttribute("instances", defaultLog);

        return "drivinglog";
    }

    /**
     * Handler for post requests on /drivinglog
     *
     * Fetches a different tímabil of the log
     *
     * @param driving Driving
     * @param user User
     * @param session HttpSession
     * @param model Model
     * @param dateHelper DateHelper
     * @return clock A view for clocking in
     */
    @RequestMapping(value="/drivinglog", method = RequestMethod.POST)
    public String monthPost(Driving driving, User user, HttpSession session, Model model, DateHelper dateHelper) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser == null ) {
            return "redirect:/login";
        }
        loadUserComponent(sessionUser, model);

        List<Driving> sessionLog = drivingService.findAllBySSNAndDagsBetween
                (
                sessionUser.getSSN(),
                dateHelper.getDate1().minusDays(1),
                dateHelper.getDate1().plusMonths(1)
                );

        model.addAttribute("instances", sessionLog);

        return "drivinglog";
    }


    /**
     * Handler for post requests on /drivinglog/new
     *
     * Appends a new driving session to the relevant log
     *
     * @param driving Driving
     * @param user User
     * @param session HttpSession
     * @param model Model
     * @return clock A view for clocking in
     */
    @RequestMapping(value="/drivinglog/new", method = RequestMethod.POST)
    public String drivingPOST(Driving driving, User user, HttpSession session, Model model){
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser == null ) {
            return "redirect:/login";
        }
        loadUserComponent(sessionUser, model);
        model.addAttribute("activePage", "drivingLog");

        driving.setSSN(sessionUser.getSSN());
        driving.setDags(LocalDate.now());
        drivingService.save(driving);
        return "redirect:/drivinglog";

    }
    /**
     *  Help function to load necessary component on authorized site.
     * @param sessionUser Current logged in user
     * @param model model of current site
     */
    public void loadUserComponent(User sessionUser, Model model) {
        model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
        model.addAttribute("abbreviation",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName().charAt(0) + "" + employeeService.findBySSN(sessionUser.getSSN()).getLastName().charAt(0)));
        model.addAttribute("fullName",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName() + " " + employeeService.findBySSN(sessionUser.getSSN()).getLastName()));
        model.addAttribute("userRole",sessionUser.getAccounttype()); // Used to display the right nav bar
    }
}
