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
import is.hi.hbv501g.group8.Persistence.Entities.Transaction;
import is.hi.hbv501g.group8.Persistence.Entities.User;
import is.hi.hbv501g.group8.Services.EmployeeService;
import is.hi.hbv501g.group8.Services.TransactionService;
import is.hi.hbv501g.group8.Services.UserService;
import is.hi.hbv501g.group8.Utilities.Status;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
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
    TransactionService transactionService;

    /**
     * Constructor for UserController
     *
     * @param userService UserService
     * @param employeeService EmployeeService
     * @param transactionService TransactionService
     */

    @Autowired
    public UserController(UserService userService, EmployeeService employeeService,TransactionService transactionService) {
        this.userService = userService;
        this.employeeService = employeeService;
        this.transactionService = transactionService;
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

        if(userService.findByUsername(user.getUsername()) == null) {
            Employee newEmployeeProfile = new Employee();
            newEmployeeProfile.setSSN(user.getSSN());
            newEmployeeProfile.setUsername(user.getUsername());
            newEmployeeProfile.setPassword(userService.secureIt(user.getPassword()));
            newEmployeeProfile.setFirstName(user.getUsername());
            newEmployeeProfile.setLastName("To be changed");
            newEmployeeProfile.setCompany("To be changed");
            newEmployeeProfile.setPhoneNumber("To be changed");
            newEmployeeProfile.setSalary(0);
            newEmployeeProfile.setStartDate(LocalDate.now());
            newEmployeeProfile.setJobTitle("To be changed");
            newEmployeeProfile.setEmail(user.getEmail());
            newEmployeeProfile.setAccounttype(2); // 0 - Admin, 1 - Manager, 2 - User
            employeeService.save(newEmployeeProfile);
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
    public ModelAndView getProfile(Model model, HttpSession session, User user){
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        model.addAttribute("activePage", "settings");
        loadUserComponent(sessionUser,model);


        ModelAndView editView = new ModelAndView("profile");
        Employee employee = employeeService.findBySSN(sessionUser.getSSN());
        employee.setSsnEmployee(employee.getSSN());
        editView.addObject("employee",employee);


        return editView;
    }



    /**
     * Handler for GET requests on /employees
     *
     * @param model Model
     * @param session HttpSession
     * @param user User
     * @return employees A view for employees
     */
    @RequestMapping(value = "employees", method = RequestMethod.POST)
    public String postEmployees(Model model, HttpSession session, User user) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser == null || sessionUser.getAccounttype() != 0) {
            return "redirect:/login";
        }
        loadUserComponent(sessionUser,model);
        model.addAttribute("activePage", "employees");
        return "employees";
    }
    /**
     * Handler for POST requests on /employees
     *
     * @param model Model
     * @param session HttpSession
     * @param user User
     * @return employees A view for employees
     */
    @RequestMapping(value = "employees", method = RequestMethod.GET)
    public String getEmployees(Model model, HttpSession session, User user) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser == null && sessionUser.getAccounttype() != 0) {
            return "redirect:/login";
        }
        loadUserComponent(sessionUser,model);
        model.addAttribute("activePage", "employees");
        model.addAttribute("employees",getEmployeeList());
        return "employees";
    }

    /**
     * Handler for POST requests on /realtimeinsights
     *
     * @param model Model
     * @param session HttpSession
     * @param user User
     * @return realtimeinsights, A view for realtimeinsights
     */
    @RequestMapping(value = "realtimeinsights", method = RequestMethod.POST)
    public String postRealTimeInsights(Model model, HttpSession session, User user) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }
        loadUserComponent(sessionUser,model);
        model.addAttribute("activePage", "realTimeInsight");
        return "realtimeinsights";
    }

    /**
     * Handler for GET requests on /realtimeinsights
     * 
     * @param model Model
     * @param session HttpSession
     * @param user User
     * @return realtimeinsights, A view for realtimeinsights
     */
    @RequestMapping(value = "realtimeinsights", method = RequestMethod.GET)
    public String getRealTimeInsights(Model model, HttpSession session, User user) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }
        loadUserComponent(sessionUser,model);
        model.addAttribute("activePage", "realTimeInsight");
        model.addAttribute("employees",getEmployeeList());
        return "realtimeinsights";
    }

    public List<Employee> getEmployeeList() {
        List<Employee> allEmployees;
        allEmployees = employeeService.findAll();

        for(Employee row : allEmployees) {
            row.setFirstNameOfEmployee(row.getFirstName());
            row.setLastNameOfEmployee(row.getLastName());
            row.setPhoneNumberEmployee(row.getPhoneNumber());
            row.setSsnEmployee(row.getSSN());
            row.setEmployeeUserName(row.getUsername());
            if(transactionService.findBySSNAndFinished(row.getSSN(), false) == null ){
                row.setStatus("offDuty");
            }
            else {
                row.setStatus("onDuty");
            }
        }
        return allEmployees;
    }

    /**
     * Handler for editing ssn
     *
     * @param ssn String object
     * @return edited ssn
     */
    @RequestMapping("/edit/{ssn}")
    public ModelAndView showEditUserPage(@PathVariable(name= "ssn") String ssn, HttpSession session, User user,Model model) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        model.addAttribute("activePage", "employees");
        loadUserComponent(sessionUser,model);

        ModelAndView editView = new ModelAndView("edit-employees");
        Employee employee = employeeService.findBySSN(ssn);
        employee.setSsnEmployee(employee.getSSN());
        editView.addObject("employee",employee);

        return editView;
    }

    /**
     * Handler for deleting ssn
     *
     * @param SSN String object
     * @return deleted SSN
     */
    @RequestMapping("delete/{ssn}")
    public String deleteEmployee(@PathVariable(name = "SSN") String SSN) {
        Employee temp = employeeService.findBySSN(SSN);
        employeeService.delete(temp);
        return "redirect:/";
    }
    /**
     *  Help function to load necessary component on authorized site.
     * @param sessionUser Current logged in user
     * @param model model of current site
     */
    public void loadUserComponent(User sessionUser,Model model){
        model.addAttribute("username", sessionUser.getUsername().toUpperCase() + " - Overview");
        model.addAttribute("abbreviation",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName().charAt(0) + "" + employeeService.findBySSN(sessionUser.getSSN()).getLastName().charAt(0)));
        model.addAttribute("fullName",(employeeService.findBySSN(sessionUser.getSSN()).getFirstName() + " " + employeeService.findBySSN(sessionUser.getSSN()).getLastName()));
        model.addAttribute("userRole",sessionUser.getAccounttype()); // Used to display the right nav bar
    }
}
