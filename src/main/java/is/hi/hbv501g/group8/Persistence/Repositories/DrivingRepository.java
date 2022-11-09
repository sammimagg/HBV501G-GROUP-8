package is.hi.hbv501g.group8.Persistence.Repositories;

import is.hi.hbv501g.group8.Persistence.Entities.Driving;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrivingRepository extends JpaRepository<Driving, String> {
    Driving save(Driving driving);
    void delete(Driving driving);
    List<Driving> findAll();
    List<Driving> findAllBySSN(String ssn);
}
