/**
 * Schedule Controller
 *
 * Description: Controller for everything to do with
 *              Work schedule! :)
 *
 * @// TODO: 22.10.2022
 *
 * @author Kristófer Breki Gylfason - kbg15@hi.is
 * @author Halldór Jens Vilhjálsson - hjv6@hi.is
 * @author Samúel Magnússon - sam38@hi.is
 */

package is.hi.hbv501g.group8.Controllers;

import is.hi.hbv501g.group8.Persistence.Entities.*;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
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

    /**
     * Handler for POST requests on schedule-admin
     *
     * 
     *
     * @param model Model
     * @param 
     * @return clock A view for clocking in
     */
    @RequestMapping(value = "schedule-admin", method = RequestMethod.POST)
    public String postScheduleAdmin(Model model, HttpSession session, User user,  TimeAndDate timeAndDate) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }
        System.out.println(timeAndDate.getDateOne() + " " + timeAndDate.getTimeTwo());
        System.out.println(timeAndDate.getId());

        Schedule schedule = new Schedule();
        schedule.setSSN(timeAndDate.getId());
        // Formatter of String
        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter TIMEFORMATTER = DateTimeFormatter.ofPattern("HH:mm");
        // Format string to date and time
        LocalDate dateOne = LocalDate.parse(timeAndDate.getDateOne(),DATEFORMATTER);
        LocalTime timeOne = LocalTime.parse(timeAndDate.getTimeOne(),TIMEFORMATTER);
        // Format string to date and time
        LocalDate dateTwo = LocalDate.parse(timeAndDate.getDateTwo(),DATEFORMATTER);
        LocalTime timeTwo = LocalTime.parse(timeAndDate.getTimeTwo(),TIMEFORMATTER);
        // Make date and time
        LocalDateTime dateTimeFrom = LocalDateTime.of(dateOne,timeOne);
        LocalDateTime dateTimeTo = LocalDateTime.of(dateTwo, timeTwo);
        // Make new schedule
        schedule.setDateAndTimeFrom(dateTimeFrom);
        schedule.setDateAndTimeTo(dateTimeTo);
        // Save new schedule
        scheduleService.save(schedule);

        return "redirect:/schedule-admin";
    }
    @RequestMapping(value = "schedule-admin", method = RequestMethod.GET)
    public String getScheduleAdmin(Model model, HttpSession session, User user, TimeAndDate timeAndDate) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }
        loadUserComponent(sessionUser,model);
        model.addAttribute("employees",getEmployeeList());

        return "schedule-admin";
    }
    @RequestMapping(value = "schedule", method = RequestMethod.GET)
    public String getSchedule(Model model,HttpSession session, User user){
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }
        loadUserComponent(sessionUser,model);
        model.addAttribute("activePage", "schedule");
        List<Schedule> allSchedule = scheduleService.findAllBySSN(sessionUser.getSSN());
        ArrayList<TimeAndDate> allTimeAndDate = new ArrayList<TimeAndDate>();

        for(int i = 0; i< allSchedule.size(); i++){
            TimeAndDate timeAndDate = new TimeAndDate();
            timeAndDate.setTimeOne(allSchedule.get(i).getDateAndTimeFrom().toLocalTime().toString());
            timeAndDate.setTimeTwo(allSchedule.get(i).getDateAndTimeTo().toLocalTime().toString());
            timeAndDate.setDateOne(allSchedule.get(i).getDateAndTimeFrom().toLocalDate().toString());
            timeAndDate.setDateTwo(allSchedule.get(i).getDateAndTimeTo().toLocalDate().toString());
            allTimeAndDate.add(i,timeAndDate);
        }

        model.addAttribute("TimeAndDate",allTimeAndDate);

        return "schedule";
    }
    public List<Employee> getEmployeeList() {
        List<Employee> allEmployees;
        allEmployees = employeeService.findAll();

        for(Employee row : allEmployees) {
            row.setFirstNameOfEmployee(row.getFirstName());
            row.setLastNameOfEmployee(row.getLastName());
            row.setSsnEmployee(row.getSSN());
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
