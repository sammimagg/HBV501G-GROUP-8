package is.hi.hbv501g.group8.RESTControllers;

import is.hi.hbv501g.group8.Persistence.Entities.*;
import is.hi.hbv501g.group8.Services.EmployeeService;
import is.hi.hbv501g.group8.Services.TransactionService;
import is.hi.hbv501g.group8.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/employee")
public class EmployeeRestController {

    UserService userService;
    EmployeeService employeeService;
    TransactionService transactionService;

    @Autowired
    public EmployeeRestController(UserService userService, EmployeeService employeeService, TransactionService transactionService) {
        this.userService = userService;
        this.employeeService = employeeService;
        this.transactionService = transactionService;
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public Employee EmployeePUT(@RequestBody SessionUser sessionUser) {
        System.out.println("test");
        return employeeService.findBySSN(sessionUser.getSsn());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public HttpStatus EmployeeGET(@RequestBody Employee emp) {

        Employee employee_to_change = employeeService.findBySSN(emp.getSSN());
        if (employee_to_change == null) return HttpStatus.BAD_REQUEST;
        System.out.println(emp.getSSN());
        employee_to_change.setUsername(emp.getUsername());
        employee_to_change.setEmail(emp.getEmail());
        employee_to_change.setFirstName(emp.getFirstName());
        employee_to_change.setLastName(emp.getLastName());
        employee_to_change.setPhoneNumber(emp.getPhoneNumber());
        employee_to_change.setJobTitle(emp.getJobTitle());
        employee_to_change.setAccountType(emp.getAccountType());
        System.out.println("usr: " + emp.getUsername() +
                "emil: " + emp.getEmail() +
                "first: " + emp.getFirstName() +
                "last: " + emp.getLastName() +
                "phone: " + emp.getPhoneNumber() +
                "job: " + emp.getJobTitle());

        employeeService.save(employee_to_change);

        return HttpStatus.ACCEPTED;

    }

    @RequestMapping(value = "/rti", method = RequestMethod.GET)
    public List<RealTimeInsightDAO> RealTimeInsightGET() {
        List<Employee> employee_list = employeeService.findAll();
        List<RealTimeInsightDAO> reval = new ArrayList<>();
        
        for ( Employee empl : employee_list) {
            empl.setPassword(null);
            RealTimeInsightDAO append_to_array = new RealTimeInsightDAO(empl, false);
            // Athuga hvort notandi sé innstimplaður
            Transaction currentTransaction = transactionService.findBySSNAndFinished(empl.getSSN(), false);
            if (currentTransaction != null) {
                append_to_array.setClocked_in(true);
                append_to_array.setClock_in_time(currentTransaction.getClockIn());
                append_to_array.setLast_transaction(null);
            } else {
                append_to_array.setClocked_in(false);
                append_to_array.setClock_in_time(null);
                append_to_array.setLast_transaction(transactionService.findFirstBySSNOrderByIDDesc(empl.getSSN()));
            }

            reval.add(append_to_array);
        }

        return reval;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Employee> EmployeeListGET() {
        return employeeService.findAll();
    }
}