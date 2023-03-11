/**
 * User REST Controller
 *
 * Controller for managing user accounts through RESTful API.
 * Provides endpoints for creating new accounts, retrieving user information,
 * and updating/deleting account details.
 *
 * @TODO 12. Mars 2023
 *      - Implement Signup / Edit / View users calls
 *
 * @author Halldór Jens Vilhjálsson - hjv6@hi.is
 * @author Kristófer Breki Gylfason - kbg15@hi.is
 * @author Samúel Magnússon - sam38@hi.is
 * @version 1.0
 * @since 2023-03-11
 */

package is.hi.hbv501g.group8.RESTControllers;

import is.hi.hbv501g.group8.Persistence.Entities.Employee;
import is.hi.hbv501g.group8.Persistence.Entities.User;
import is.hi.hbv501g.group8.Services.EmployeeService;
import is.hi.hbv501g.group8.Services.TransactionService;
import is.hi.hbv501g.group8.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
    private UserService userService;
    private EmployeeService employeeService;

    public UserRestController(UserService userService, EmployeeService employeeService) {
        this.userService = userService;
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody Employee newEmployee) {
        /* newEmployee frá post-inu hefur: username, password, ssn, og email */
        if (userService.findBySSN(newEmployee.getSSN()) != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("SSN already has an account");
        }
        employeeDefaultValues(newEmployee);
        employeeService.save(newEmployee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private void employeeDefaultValues(Employee newEmployee) {
        newEmployee.setFirstName(newEmployee.getUsername());
        newEmployee.setPassword(userService.secureIt(newEmployee.getPassword()));
        newEmployee.setLastName("To be changed");
        newEmployee.setCompany("To be changed");
        newEmployee.setPhoneNumber("To be changed");
        newEmployee.setSalary(0);
        newEmployee.setStartDate(LocalDate.now());
        newEmployee.setJobTitle("To be changed");
        newEmployee.setAccounttype(2);
    }
}
