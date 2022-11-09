package is.hi.hbv501g.group8.Controllers;

import is.hi.hbv501g.group8.Persistence.Entities.Driving;
import is.hi.hbv501g.group8.Persistence.Entities.Transaction;
import is.hi.hbv501g.group8.Services.DrivingService;
import is.hi.hbv501g.group8.Services.EmployeeService;
import is.hi.hbv501g.group8.Services.TransactionReviewService;
import is.hi.hbv501g.group8.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DrivingLogController {

    private DrivingService drivingService;

    @Autowired
    public DrivingLogController(DrivingService drivingService) {
        this.drivingService = drivingService;
    }

    @RequestMapping(value="/driving", method = RequestMethod.GET)
    public String drivingGET(Driving driving) {
        return "drivinglogtests";
    }

    @RequestMapping(value="/driving/new", method = RequestMethod.POST)
    public String drivingPOST(Driving driving){

        drivingService.save(driving);

        return "drivinglogtests";
    }
}
