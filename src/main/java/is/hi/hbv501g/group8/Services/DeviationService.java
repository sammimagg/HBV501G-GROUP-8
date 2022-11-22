package is.hi.hbv501g.group8.Services;

import is.hi.hbv501g.group8.Persistence.Entities.Deviation;

import java.util.List;

public interface DeviationService {
    Deviation save(Deviation deviation);
    void delete(Deviation deviation);
    List<Deviation> findAll();
    List<Deviation> findAllBySSN(String ssn);
    List<Deviation> findAllByType(Deviation.TYPE type);
}
