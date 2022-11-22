package is.hi.hbv501g.group8.Persistence.Repositories;

import is.hi.hbv501g.group8.Persistence.Entities.Deviation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviationRepository extends JpaRepository<Deviation, String> {
    Deviation save(Deviation deviation);
    void delete(Deviation deviation);
    List<Deviation> findAll();
    List<Deviation> findAllBySSN(String ssn);
    List<Deviation> findAllByType(Deviation.TYPE type);
}
