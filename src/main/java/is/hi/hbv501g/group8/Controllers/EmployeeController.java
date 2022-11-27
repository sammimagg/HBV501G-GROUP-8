/**
 * Employee Controller
 *
 * Description: Controller for editing employee information
 *              used by managers/admins
 *
 * @// TODO: 22.10.2022
 *
 * @author kristófer Breki Gylfason - kbg15@hi.is
 * @author Halldór Jens Vilhjálsson - hjv6@hi.is
 * @author Samúel Magnússon - sam38@hi.is
 */
package is.hi.hbv501g.group8.Controllers;

import is.hi.hbv501g.group8.Persistence.Entities.Employee;
import is.hi.hbv501g.group8.Persistence.Entities.User;
import is.hi.hbv501g.group8.Services.EmployeeService;
import is.hi.hbv501g.group8.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class EmployeeController {

    EmployeeService employeeService;
    UserService userService;

    public EmployeeController(EmployeeService employeeService, UserService userService) {
        this.employeeService = employeeService;
        this.userService = userService;
    }

    /**
     * Handler for POST requests on /save
     *
     * Edits employee information
     *
     * @param driving Driving
     * @param user User
     * @param session HttpSession
     * @param model Model
     * @param dateHelper DateHelper
     * @return clock A view for clocking in
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String submitChangesEmployee(Model model, Employee employee, HttpSession session) {

        if(ensureAuthorization(session)) return "redirect:/login";

        // Update Employee
        Employee workingPloyee = employeeService.findBySSN(employee.getSSN());
        workingPloyee.setFirstName(employee.getFirstName());
        workingPloyee.setLastName(employee.getLastName());
        workingPloyee.setEmail(employee.getEmail());
        workingPloyee.setPhoneNumber(employee.getPhoneNumber());
        workingPloyee.setAccounttype(employee.getAccounttype());
        employeeService.save(workingPloyee);

        return "redirect:/employees";
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
