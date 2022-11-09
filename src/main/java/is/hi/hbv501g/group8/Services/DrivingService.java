package is.hi.hbv501g.group8.Services;

import is.hi.hbv501g.group8.Persistence.Entities.Driving;

import java.util.List;

public interface DrivingService {
    Driving save(Driving driving);
    void delete(Driving driving);
    List<Driving> findAll();
    List<Driving> findAllBySSN(String ssn);
}
