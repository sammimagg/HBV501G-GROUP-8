package is.hi.hbv501g.group8.RESTControllers;

import is.hi.hbv501g.group8.Persistence.Entities.Schedule;
import is.hi.hbv501g.group8.Services.EmployeeService;
import is.hi.hbv501g.group8.Services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/schedule")
public class ScheduleRestController {

    private final EmployeeService employeeService;
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleRestController(EmployeeService employeeService, ScheduleService scheduleService) {
        this.employeeService = employeeService;
        this.scheduleService = scheduleService;
    }

    /**
     * Easter Egg for Samuel.
     */
    @RequestMapping(value = "/sammi", method = RequestMethod.GET)
    public ResponseEntity<?> easterEgg(){
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    /**
     * Creates a new schedule for selected employee
     *
     * @param schedule Schedule, needs:
     *                 String SSN, LocalDateTime from & to
     * @return HttpStatus
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<?> newSchedule(@RequestBody Schedule schedule){
        scheduleService.save(schedule);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(value = "/{ssn}", method = RequestMethod.GET)
    public List<Schedule> fetchSchedule(@PathVariable String ssn){
        List<Schedule> reval = scheduleService.findAllBySSN(ssn);
        return reval;
    }
}