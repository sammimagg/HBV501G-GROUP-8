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

import is.hi.hbv501g.group8.Persistence.Entities.Transaction;
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
     * @param model Model
     * @param session HttpSession
     * @param user User
     * @param transactionReview TransactionReview
     * @return reviews A view for reviews
     */
    @RequestMapping(value = "reviews", method = RequestMethod.POST)
    public String postRequest(Model model, HttpSession session, User user, TransactionReview transactionReview) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }
        loadUserComponent(sessionUser,model);
        model.addAttribute("activePage", "requestReview");

        TransactionReview temp = transactionReviewService.findByID(transactionReview.getID());
        Transaction tmp = transactionService.findByID(transactionReview.getID());
        if(tmp == null || temp == null) return "redirect:/";
        if( transactionReview.getStatus().equals("approve")) {
            temp.setStatus("accepted");
            tmp.setClockIn(temp.getChangedClockIn());
            tmp.setClockOut(temp.getChangedClockOut());
            tmp.setStatus("request accepted");

        } else if (transactionReview.getStatus().equals("reject")) {
            temp.setStatus("rejected");
            tmp.setStatus("request rejected");
        }

        transactionReviewService.save(temp);
        transactionService.save(tmp);


        return "reviews";
    }

    /**
     * A handler for GET requests on /reviews
     *
     * @param model Model
     * @param session HttpSession
     * @param user User
     * @param transactionReview TransactionReview
     * @return reviews A view for reviews
     */
    @RequestMapping(value = "reviews", method = RequestMethod.GET)
    public String getRequest(Model model, HttpSession session, User user, TransactionReview transactionReview) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }
        loadUserComponent(sessionUser,model);
        model.addAttribute("activePage", "requestReview");

        List<TransactionReview> pendingReview = transactionReviewService.findAllByStatus("pending");
        for (TransactionReview r : pendingReview) {
            System.out.println(r.getSSN());
        }
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