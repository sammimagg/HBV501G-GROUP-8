package is.hi.hbv501g.group8.Controllers;

import is.hi.hbv501g.group8.Persistence.Entities.DateHelper;
import is.hi.hbv501g.group8.Persistence.Entities.Employee;
import is.hi.hbv501g.group8.Persistence.Entities.Schedule;
import is.hi.hbv501g.group8.Persistence.Entities.User;
import is.hi.hbv501g.group8.Services.DeviationService;
import is.hi.hbv501g.group8.Services.EmployeeService;
import is.hi.hbv501g.group8.Services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class ScheduleController {
    EmployeeService employeeService;
    ScheduleService scheduleService;


    @Autowired
    public ScheduleController(EmployeeService employeeService, ScheduleService scheduleService) {
        this.employeeService = employeeService;
        this.scheduleService = scheduleService;
    }
    @RequestMapping(value = "schedule-admin", method = RequestMethod.POST)
    public String postRequest(Model model, HttpSession session, User user, DateHelper dateHelper) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        //scheduleService.save(schedule);

        return "redirect:/schedule-admin";
    }
    @RequestMapping(value = "schedule-admin", method = RequestMethod.GET)
    public String getRequest(Model model, HttpSession session, User user, DateHelper dateHelper) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }
        loadUserComponent(sessionUser,model);
        model.addAttribute("employees",getEmployeeList());

        return "schedule-admin";
    }

    public List<Employee> getEmployeeList() {
        List<Employee> allEmployees;
        allEmployees = employeeService.findAll();

        for(Employee row : allEmployees) {
            row.setFirstNameOfEmployee(row.getFirstName());
            row.setLastNameOfEmployee(row.getLastName());
        }
        return allEmployees;
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
        model.addAttribute("activePage", "makeSchedule");

    }
}
