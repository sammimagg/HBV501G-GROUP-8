package is.hi.hbv501g.group8.Persistence.Repositories;

import is.hi.hbv501g.group8.Persistence.Entities.Driving;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {
    Schedule save(Schedule schedule);
    void delete(Schedule schedule);
    List<Schedule> findAll();
    List<Schedule> findAllBySSN(String ssn);
}
