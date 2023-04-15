package is.hi.hbv501g.group8.RESTControllers;

import is.hi.hbv501g.group8.Persistence.Entities.DateHelper;
import is.hi.hbv501g.group8.Persistence.Entities.Driving;
import is.hi.hbv501g.group8.Services.DrivingService;
import is.hi.hbv501g.group8.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/driving")
public class DrivingLogRestController {
    private final DrivingService drivingService;
    private final EmployeeService employeeService;

    @Autowired
    public DrivingLogRestController(DrivingService drivingService, EmployeeService employeeService) {
        this.drivingService = drivingService;
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/{ssn}", method = RequestMethod.PUT)
    public List<Driving> fetchDrivingLog(@PathVariable String ssn,
                                         @RequestParam(required = false) LocalDate dateFrom,
                                         @RequestParam(required = false) LocalDate dateTo
                                         ){
        if (employeeService.findBySSN(ssn) == null) {
            return null;
        }

        LocalDate currentDate = LocalDate.of(
                LocalDate.now().getYear(),
                LocalDate.now().getMonth(),
                1
        );
        LocalDate currentEndDate = currentDate.plusMonths(1);

        LocalDate inputDateFromFixed = dateFrom != null ? dateFrom : currentDate;
        LocalDate inputDateToFixed = dateTo != null ? dateTo : currentEndDate;

        return drivingService.findAllBySSNAndDagsBetween(ssn, inputDateFromFixed, inputDateToFixed);
    }

    /**
     * Creates a new Driving entity in the Driving log with the information provided in a POST request.
     * @param drivingSession
     * @return HttpStatus
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<?> appendDrivingLog(@RequestBody Driving drivingSession) {
        //Driving alreadyExists = drivingService.findBySSNAndDags(drivingSession.getSSN(), drivingSession.getDags());
        drivingService.save(drivingSession);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
