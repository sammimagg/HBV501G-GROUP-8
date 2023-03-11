package is.hi.hbv501g.group8.RESTControllers;

import is.hi.hbv501g.group8.Persistence.Entities.DateHelper;
import is.hi.hbv501g.group8.Persistence.Entities.Driving;
import is.hi.hbv501g.group8.Services.DrivingService;
import is.hi.hbv501g.group8.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/driving")
public class DrivingLogRestController {
    private final DrivingService drivingService;
    private final EmployeeService employeeService;

    @Autowired
    public DrivingLogRestController(DrivingService drivingService, EmployeeService employeeService) {
        this.drivingService = drivingService;
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/{ssn}", method = RequestMethod.GET)
    public List<Driving> fetchDrivingLog(@RequestBody DateHelper dateHelper, @PathVariable String ssn){
        if (employeeService.findBySSN(ssn) == null) {
            return null;
        }

        LocalDate currentDate = LocalDate.of(
                LocalDate.now().getYear(),
                LocalDate.now().getMonth(),
                1
        );
        LocalDate currentEndDate = currentDate.plusMonths(1);

        LocalDate inputDateFrom = dateHelper.getDate1();
        LocalDate inputDateTo = dateHelper.getDate2();

        LocalDate inputDateFromFixed = inputDateFrom != null ? inputDateFrom : currentDate;
        LocalDate inputDateToFixed = inputDateTo != null ? inputDateTo : currentEndDate;

        // debug:
        System.out.println(currentDate + " : " + currentEndDate + " : " +
                            inputDateFromFixed + " : " + inputDateTo + " : " +
                            inputDateFromFixed + " : " + inputDateToFixed);

        return drivingService.findAllBySSNAndDagsBetween(ssn, inputDateFromFixed, inputDateToFixed);
    }


}
