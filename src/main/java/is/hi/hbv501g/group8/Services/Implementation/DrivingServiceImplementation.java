package is.hi.hbv501g.group8.Services.Implementation;

import is.hi.hbv501g.group8.Persistence.Entities.Driving;
import is.hi.hbv501g.group8.Persistence.Repositories.DrivingRepository;
import is.hi.hbv501g.group8.Services.DrivingService;

import java.util.List;

public class DrivingServiceImplementation implements DrivingService {

    private DrivingRepository drivingRepository;

    public DrivingServiceImplementation(DrivingRepository drivingRepository) {
        this.drivingRepository = drivingRepository;
    }

    /**
     * @param driving
     * @return
     */
    @Override
    public Driving save(Driving driving) {
        return drivingRepository.save(driving);
    }

    /**
     * @param driving
     */
    @Override
    public void delete(Driving driving) {
        drivingRepository.delete(driving);
    }

    /**
     * @return
     */
    @Override
    public List<Driving> findAll() {
        return drivingRepository.findAll();
    }

    /**
     * @param ssn
     * @return
     */
    @Override
    public List<Driving> findAllBySSN(String ssn) {
        return drivingRepository.findAllBySSN(ssn);
    }
}
