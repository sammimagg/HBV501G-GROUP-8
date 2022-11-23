/**
 * Review Controller
 *
 * Description: Controller for everything to do with
 *              responding to requests. (Transaction Review)
 *              
 * @// TODO: 22.10.2022
 *
 * @author kristófer Breki Gylfason - kbg15@hi.is
 * @author Halldór Jens Vilhjálsson - hjv6@hi.is
 * @author Samúel Magnússon - sam38@hi.is
 */

package is.hi.hbv501g.group8.Controllers;

import is.hi.hbv501g.group8.Persistence.Entities.TransactionReview;
import is.hi.hbv501g.group8.Persistence.Entities.User;
import is.hi.hbv501g.group8.Services.EmployeeService;
import is.hi.hbv501g.group8.Services.TransactionReviewService;
import is.hi.hbv501g.group8.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ReviewController {
    private final TransactionReviewService transactionReviewService;
    private final TransactionService transactionService;
    private final EmployeeService employeeService;

    @Autowired
    public ReviewController(TransactionReviewService transactionReviewService, TransactionService transactionService, EmployeeService employeeService) {
        this.transactionReviewService = transactionReviewService;
        this.transactionService = transactionService;
        this.employeeService = employeeService;
    }


    /**
     * Handler for POST requests on /reviews
     *
     * @// TODO: 22.10.2022
     *
     * @param model Model
     * @param session HttpSession
     * @param user User
     * @return reviews A view for reviews
     */
    @RequestMapping(value = "reviews", method = RequestMethod.POST)
    public String postRequest(Model model, HttpSession session, User user) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser!=null) {
            model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
            model.addAttribute("abbreviation",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName().charAt(0) + "" + employeeService.findBySSN(sessionUser.getSSN()).getLastName().charAt(0)));
            model.addAttribute("fullName",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName() + " " + employeeService.findBySSN(sessionUser.getSSN()).getLastName()));
            model.addAttribute("userRole",sessionUser.getAccounttype()); // Used to display the right nav bar
            model.addAttribute("activePage", "requestReview");
        }
        return "reviews";
    }

    /**
     * A handler for GET requests on /reviews
     *
     * @// TODO: 22.10.2022
     *
     * @param model Model
     * @param session HttpSession
     * @param user User
     * @return reviews A view for reviews
     */
    @RequestMapping(value = "reviews", method = RequestMethod.GET)
    public String getRequest(Model model, HttpSession session, User user) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser!=null) {
            model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
            model.addAttribute("abbreviation",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName().charAt(0) + "" + employeeService.findBySSN(sessionUser.getSSN()).getLastName().charAt(0)));
            model.addAttribute("fullName",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName() + " " + employeeService.findBySSN(sessionUser.getSSN()).getLastName()));
            model.addAttribute("userRole",sessionUser.getAccounttype()); // Used to display the right nav bar
            model.addAttribute("activePage", "requestReview");
        }
        // Væri flott að gera eh "U not have access" síðu
        if(ensureAuthorization(session)) return "redirect:/login";
        List<TransactionReview> pendingReview = transactionReviewService.findAllByStatus("pending");
        model.addAttribute("reviews", pendingReview);

        return "reviews";
    }

    /**
     * Function that ensures that logged in user is Manager or Admin
     *
     * @param session HttpSession
     * @return true þá og því aðeins að enginn notandi er tengdur eða
     *         tengdur notandi er skilgreindur sem 2 eða hærra, þ.e. Admin er 0,
     *         Manager er 1, Venjulegir notendur 2
     */
    private boolean ensureAuthorization(HttpSession session){
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        return sessionUser == null || sessionUser.getAccounttype() > 1;
    }
}