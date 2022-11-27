package is.hi.hbv501g.group8.Services;

import is.hi.hbv501g.group8.Persistence.Entities.Schedule;
import is.hi.hbv501g.group8.Persistence.Entities.TimeAndDate;

import java.util.List;

public interface ScheduleService {
    Schedule save(Schedule schedule);

    void delete(Schedule schedule);

    List<Schedule> findAll();

    List<Schedule> findAllBySSN(String ssn);


}
