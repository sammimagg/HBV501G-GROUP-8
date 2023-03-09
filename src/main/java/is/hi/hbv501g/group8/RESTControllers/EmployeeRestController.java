package is.hi.hbv501g.group8.RESTControllers;

import is.hi.hbv501g.group8.Persistence.Entities.Employee;
import is.hi.hbv501g.group8.Persistence.Entities.RealTimeInsightDAO;
import is.hi.hbv501g.group8.Persistence.Entities.Transaction;
import is.hi.hbv501g.group8.Services.EmployeeService;
import is.hi.hbv501g.group8.Services.TransactionService;
import is.hi.hbv501g.group8.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(value = "/rti", method = RequestMethod.GET)
    public List<RealTimeInsightDAO> RealTimeInsightGET() {
        List<Employee> employee_list = employeeService.findAll();
        List<RealTimeInsightDAO> reval = new ArrayList<>();
        
        for ( Employee empl : employee_list) {
            RealTimeInsightDAO append_to_array = new RealTimeInsightDAO(empl, false);

            // Athuga hvort notandi sé innstimplaður
            Transaction currentTransaction = transactionService.findBySSNAndFinished(empl.getSSN(), false);
            if (currentTransaction != null) {
                append_to_array.setClocked_in(true);
                append_to_array.setClock_in_time(currentTransaction.getClockIn());
            }

            reval.add(append_to_array);
        }

        System.out.println("test");

        return reval;
    }
}