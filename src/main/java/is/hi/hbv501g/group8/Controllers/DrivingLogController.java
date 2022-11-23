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


    @RequestMapping(value="/drivinglog", method = RequestMethod.GET)
    public String drivingGET(Driving driving, User user, HttpSession session, Model model, DateHelper dateHelper) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser == null ) {
            return "redirect:/login";
        }
        else {
            model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
            model.addAttribute("abbreviation",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName().charAt(0) + "" + employeeService.findBySSN(sessionUser.getSSN()).getLastName().charAt(0)));
            model.addAttribute("fullName",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName() + " " + employeeService.findBySSN(sessionUser.getSSN()).getLastName()));
            model.addAttribute("userRole",sessionUser.getAccounttype()); // Used to display the right nav bar
        }
        model.addAttribute("activePage", "drivingLog");

        LocalDate currentDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);

        List<Driving> defaultLog = drivingService.findAllBySSNAndDagsBetween(sessionUser.getSSN(),
                currentDate, currentDate.plusMonths(1));
        model.addAttribute("instances", defaultLog);

        return "drivinglog";
    }

    @RequestMapping(value="/drivinglog", method = RequestMethod.POST)
    public String monthPost(Driving driving, User user, HttpSession session, Model model, DateHelper dateHelper) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
            model.addAttribute("abbreviation",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName().charAt(0) + "" + employeeService.findBySSN(sessionUser.getSSN()).getLastName().charAt(0)));
            model.addAttribute("fullName",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName() + " " + employeeService.findBySSN(sessionUser.getSSN()).getLastName()));
            model.addAttribute("userRole",sessionUser.getAccounttype()); // Used to display the right nav bar
        }
        System.out.println(dateHelper.getDate1());

        List<Driving> sessionLog = drivingService.findAllBySSNAndDagsBetween(sessionUser.getSSN(),
                dateHelper.getDate1().minusDays(1), dateHelper.getDate1().plusMonths(1));

        model.addAttribute("instances", sessionLog);

        return "drivinglog";
    }



    @RequestMapping(value="/drivinglog/new", method = RequestMethod.POST)
    public String drivingPOST(Driving driving, User user, HttpSession session, Model model){
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser == null ) {
            return "redirect:/login";
        }
        else {
            model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
            model.addAttribute("abbreviation",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName().charAt(0) + "" + employeeService.findBySSN(sessionUser.getSSN()).getLastName().charAt(0)));
            model.addAttribute("fullName",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName() + " " + employeeService.findBySSN(sessionUser.getSSN()).getLastName()));
            model.addAttribute("userRole",sessionUser.getAccounttype()); // Used to display the right nav bar
        }
        model.addAttribute("activePage", "drivingLog");
        driving.setSSN(sessionUser.getSSN());
        driving.setDags(LocalDate.now());
        drivingService.save(driving);
        return "redirect:/drivinglog";

        // Taka út:
        //List<Driving> pp = drivingService.findAllBySSNAndDagsBetween(sessionUser.getSSN(), LocalDate eh.at)
    }
}
