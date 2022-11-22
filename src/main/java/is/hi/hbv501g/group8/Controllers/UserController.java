/**
 * User Controller
 *
 * Description: Controller for everything to do with users
 *
 * @author kristófer Breki Gylfason - kbg15@hi.is
 * @author Halldór Jens Vilhjálsson - hjv6@hi.is
 * @author Samúel Magnússon - sam38@hi.is
 */
package is.hi.hbv501g.group8.Controllers;

import is.hi.hbv501g.group8.Persistence.Entities.DateHelper;
import is.hi.hbv501g.group8.Persistence.Entities.Employee;
import is.hi.hbv501g.group8.Persistence.Entities.User;
import is.hi.hbv501g.group8.Services.EmployeeService;
import is.hi.hbv501g.group8.Services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
public class UserController {
    UserService userService;
    EmployeeService employeeService;

    /**
     * Constructor for UserController
     *
     * @param userService UserService
     * @param employeeService EmployeeService
     */

    @Autowired
    public UserController(UserService userService, EmployeeService employeeService) {
        this.userService = userService;
        this.employeeService = employeeService;
    }

    /**
     * A handler for GET requests on /signup
     *
     * @param user User object
     * @return signup A view for a sign-up page
     */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupGET(User user){
        return "signup";
    }

    /**
     * A handler for POST requests on /signup
     *
     * This functions searches for existing users with the same username.
     * If a User object already exists with said username, a new User is not created.
     *
     * @param user User object
     * @param result BindingResult
     * @param model Model
     *
     * @return redirect depending on results
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPOST(User user, BindingResult result, Model model){
        if(result.hasErrors()) {
            return "redirect:/signup";
        }
        User exists = userService.findByUsername(user.getUsername());

        if(exists == null) {
            //userService.save(user);
            Employee newEmployeeProfile = new Employee();
            newEmployeeProfile.setSSN(user.getSSN());
            newEmployeeProfile.setUsername(user.getUsername());
            /*
            *  þið hafið ábyggilega séð djók comments eeins og
            *  "ekki taka út, allt brotnar"
            * þetta er svona komment
            * ekki breyta
            */
            newEmployeeProfile.setPassword(userService.secureIt(user.getPassword()));
            newEmployeeProfile.setFirstName(user.getUsername());
            newEmployeeProfile.setLastName("To be changed");
            newEmployeeProfile.setCompany("To be changed");
            newEmployeeProfile.setPhoneNumber("To be changed");
            newEmployeeProfile.setSalary(0);
            newEmployeeProfile.setStartDate(LocalDate.now());
            newEmployeeProfile.setJobTitle("To be changed");
            employeeService.save(newEmployeeProfile);
            /*
            pls ekki spyrja
            User fixPassword = userService.findBySSN(newEmployeeProfile.getSSN());
            fixPassword.setPassword(user.getPassword());
            userService.save(fixPassword);

             */
        }
        return "redirect:/";
    }

    /**
     * A handler for GET requests on /logout
     *
     * @param request User object
     * @return logout back to login-page view
     */
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }


    /**
     * A handler for GET requests on /login
     *
     * @param user User object
     * @return login login-page view
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET(User user) {
        return "login";
    }

    /**
     * A handler for POST requests on /login
     *
     * Authenticates user
     *
     * @param user User object
     * @param result BindingResult
     * @param model Model
     * @param session HttpSession
     * @return RedirectReview The result of the operation if logging in is successful, or a redirect with errors
     *          if the logging in procedure failed.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RedirectView loginPOST(User user, BindingResult result, Model model, HttpSession session){
        if(result.hasErrors()){
            return new RedirectView("login");
        }
        User exists = userService.login(user);
        if(exists != null){
            session.setAttribute("LoggedInUser", exists);
            model.addAttribute("LoggedInUser", exists);
            return new RedirectView("/list");
        }
        return new RedirectView("/");
    }
    /**
     * Handler for GET requests on /loggedin
     *
     * Used for testing. Will be removed
     *
     * @param session HttpSession
     * @param model Model
     * @return redirect or a view, depending on the presence of a User session.
     * @deprecated
    */
    @RequestMapping(value = "/loggedin", method = RequestMethod.GET)
    public String loggedinGET(HttpSession session, Model model){
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if(sessionUser  != null) {
            model.addAttribute("LoggedInUser", sessionUser);
            return "loggedInUser";
        }
        return "redirect:/";
    }

    /**
     * Handler for GET requests on /profile
     *
     * Fetches relevant information about User from database.
     * Displays said information using Model
     *
     * @param model Model
     * @param session HttpSession
     * @param user User
     * @return profile A view for profile
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getProfile(Model model, HttpSession session, User user){
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser!=null) {
            model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
            model.addAttribute("abbreviation",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName().charAt(0) + "" + employeeService.findBySSN(sessionUser.getSSN()).getLastName().charAt(0)));
            model.addAttribute("fullName",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName() + " " + employeeService.findBySSN(sessionUser.getSSN()).getLastName()));
            model.addAttribute("userRole",sessionUser.getAccounttype()); // Used to display the right nav bar
            model.addAttribute("firstName",employeeService.findBySSN(sessionUser.getSSN()).getFirstName());
            model.addAttribute("lastName",employeeService.findBySSN(sessionUser.getSSN()).getLastName());
            model.addAttribute("phoneNumber",employeeService.findBySSN(sessionUser.getSSN()).getPhoneNumber());
            model.addAttribute("company",employeeService.findBySSN(sessionUser.getSSN()).getCompany());
            model.addAttribute("jobtitle",employeeService.findBySSN(sessionUser.getSSN()).getJobTitle());
            model.addAttribute("startDate", employeeService.findBySSN(sessionUser.getSSN()).getStartDate());
            // VOID
            employeeService.findBySSN(sessionUser.getSSN()).updateRemainingVacationDays();
            employeeService.findBySSN(sessionUser.getSSN()).updateRemainingSickDays();
            model.addAttribute("sickDays", employeeService.findBySSN(sessionUser.getSSN()).getRemainingSickDays());
            model.addAttribute("vacationDays", employeeService.findBySSN(sessionUser.getSSN()).getRemainingVacationDays());
        }
        return "profile";
    }
    /**
     * Handler for POST requests on /profile
     *
     * Fetches relevant information about User from database.
     * Displays said information using Model
     *
     * @param model Model
     * @param session HttpSession
     * @param user User
     * @return profile A view for profile
     */
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String upateProfile(Model model, HttpSession session, User user){
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser!=null) {
            model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
            model.addAttribute("abbreviation",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName().charAt(0) + "" + employeeService.findBySSN(sessionUser.getSSN()).getLastName().charAt(0)));
            model.addAttribute("fullName",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName() + " " + employeeService.findBySSN(sessionUser.getSSN()).getLastName()));
            model.addAttribute("userRole",sessionUser.getAccounttype()); // Used to display the right nav bar
            model.addAttribute("firstName",employeeService.findBySSN(sessionUser.getSSN()).getFirstName());
            model.addAttribute("lastName",employeeService.findBySSN(sessionUser.getSSN()).getLastName());
            model.addAttribute("phoneNumber",employeeService.findBySSN(sessionUser.getSSN()).getPhoneNumber());
            model.addAttribute("company",employeeService.findBySSN(sessionUser.getSSN()).getCompany());
            model.addAttribute("jobtitle",employeeService.findBySSN(sessionUser.getSSN()).getJobTitle());
        }
        return "profile";
    }
    /**
     * Handler for GET requests on /employees
     *
     * @// TODO: 22.10.2022
     *
     * @param model Model
     * @param session HttpSession
     * @param user User
     * @return employees A view for employees
     */
    @RequestMapping(value = "employees", method = RequestMethod.POST)
    public String postEmployees(Model model, HttpSession session, User user) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser!=null) {
            model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
            model.addAttribute("abbreviation",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName().charAt(0) + "" + employeeService.findBySSN(sessionUser.getSSN()).getLastName().charAt(0)));
            model.addAttribute("fullName",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName() + " " + employeeService.findBySSN(sessionUser.getSSN()).getLastName()));
            model.addAttribute("userRole",sessionUser.getAccounttype()); // Used to display the right nav bar

        }

        return "employees";
    }
    /**
     * Handler for POST requests on /employees
     *
     * @// TODO: 22.10.2022
     *
     * @param model Model
     * @param session HttpSession
     * @param user User
     * @return employees A view for employees
     */
    @RequestMapping(value = "employees", method = RequestMethod.GET)
    public String getEmployees(Model model, HttpSession session, User user) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser!=null) {
            model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
            model.addAttribute("abbreviation",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName().charAt(0) + "" + employeeService.findBySSN(sessionUser.getSSN()).getLastName().charAt(0)));
            model.addAttribute("fullName",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName() + " " + employeeService.findBySSN(sessionUser.getSSN()).getLastName()));
            model.addAttribute("userRole",sessionUser.getAccounttype()); // Used to display the right nav bar
        }
        model.addAttribute("employees",getEmployeeList());
        return "employees";
    }

    /**
     * Handler for POST requests on /realtimeinsights
     *
     * @// TODO: 22.10.2022
     *
     * @param model Model
     * @param session HttpSession
     * @param user User
     * @return realtimeinsights A view for realtimeinsights
     */
    @RequestMapping(value = "realtimeinsights", method = RequestMethod.POST)
    public String postRealTimeInsights(Model model, HttpSession session, User user) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser!=null) {
            model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
            model.addAttribute("abbreviation",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName().charAt(0) + "" + employeeService.findBySSN(sessionUser.getSSN()).getLastName().charAt(0)));
            model.addAttribute("fullName",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName() + " " + employeeService.findBySSN(sessionUser.getSSN()).getLastName()));
            model.addAttribute("userRole",sessionUser.getAccounttype()); // Used to display the right nav bar
        }

        return "realtimeinsights";
    }

    /**
     * Handler for GET requests on /realtimeinsights
     *
     * @// TODO: 22.10.2022  
     * 
     * @param model Model
     * @param session HttpSession
     * @param user User
     * @return realtimeinsights A view for realtimeinsights
     */
    @RequestMapping(value = "realtimeinsights", method = RequestMethod.GET)
    public String getRealTimeInsights(Model model, HttpSession session, User user) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser!=null) {
            model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
            model.addAttribute("abbreviation",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName().charAt(0) + "" + employeeService.findBySSN(sessionUser.getSSN()).getLastName().charAt(0)));
            model.addAttribute("fullName",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName() + " " + employeeService.findBySSN(sessionUser.getSSN()).getLastName()));
            model.addAttribute("userRole",sessionUser.getAccounttype()); // Used to display the right nav bar
        }
        return "realtimeinsights";
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
        }
        return "reviews";
    }
    public List<Employee> getEmployeeList() {
        List<Employee> allEmployees;
        allEmployees = employeeService.findAll();

        for(Employee row : allEmployees) {
            row.setFirstNameOfEmployee(row.getFirstName());
            row.setLastNameOfEmployee(row.getLastName());
            row.setPhoneNumberEmployee(row.getPhoneNumber());
            row.setSsnEmployee(row.getSSN());
        }
        return allEmployees;
    }
    @RequestMapping("/edit/{ssn}")
    public ModelAndView showEditUserPage(@PathVariable(name= "ssn") String ssn) {
        ModelAndView editView = new ModelAndView("edit-employees");
        Employee employee = employeeService.findBySSN(ssn);
        editView.addObject("employee",employee);

        return editView;
    }
    @RequestMapping("delete/{ssn}")
    public String deleteEmployee(@PathVariable(name = "SSN") String SSN) {
        Employee temp = employeeService.findBySSN(SSN);
        employeeService.delete(temp);
        return "redirect:/";
    }

}
