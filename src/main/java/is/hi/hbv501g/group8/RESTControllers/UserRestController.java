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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

}
