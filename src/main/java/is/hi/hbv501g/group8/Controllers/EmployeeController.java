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

import is.hi.hbv501g.group8.Persistence.Entities.DateHelper;
import is.hi.hbv501g.group8.Persistence.Entities.Employee;
import is.hi.hbv501g.group8.Persistence.Entities.RequestReview;
import is.hi.hbv501g.group8.Persistence.Entities.User;
import is.hi.hbv501g.group8.Services.EmployeeService;
import is.hi.hbv501g.group8.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class EmployeeController {

    EmployeeService employeeService;
    UserService userService;

    public EmployeeController(EmployeeService employeeService, UserService userService) {
        this.employeeService = employeeService;
        this.userService = userService;
    }

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

    private boolean ensureAuthorization(HttpSession session){
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        return sessionUser == null;
    }
}
