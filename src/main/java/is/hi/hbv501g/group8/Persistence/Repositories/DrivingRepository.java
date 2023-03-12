package is.hi.hbv501g.group8.Persistence.Repositories;

import is.hi.hbv501g.group8.Persistence.Entities.Driving;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DrivingRepository extends JpaRepository<Driving, String> {
    Driving save(Driving driving);
    void delete(Driving driving);
    List<Driving> findAll();
    List<Driving> findAllBySSN(String ssn);

    List<Driving> findAllBySSNAndDagsBetween(String ssn, LocalDate startDate, LocalDate endDate);
    Driving findBySSNAndDags(String ssn, LocalDate dags);

}
