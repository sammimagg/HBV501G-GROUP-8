package is.hi.hbv501g.group8.Services;

import is.hi.hbv501g.group8.Persistence.Entities.Driving;
import is.hi.hbv501g.group8.Persistence.Entities.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DrivingService {
    Driving save(Driving driving);
    void delete(Driving driving);
    List<Driving> findAll();
    List<Driving> findAllBySSN(String ssn);

    List<Driving> findAllBySSNAndDagsBetween(String ssn, LocalDate startDate, LocalDate endDate);
    Driving findBySSNAndDags(String ssn, LocalDate dags);
}
