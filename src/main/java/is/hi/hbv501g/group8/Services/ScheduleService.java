package is.hi.hbv501g.group8.Services;

import is.hi.hbv501g.group8.Persistence.Entities.Deviation;

import java.util.List;

public interface DeviationService {
    Schedule save(Schedule schedule);
    void delete(Schedule schedule);
    List<Schedule> findAll();
    List<Schedule> findAllBySSN(String ssn);
}
