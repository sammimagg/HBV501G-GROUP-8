package is.hi.hbv501g.group8.Services.Implementation;

import is.hi.hbv501g.group8.Persistence.Entities.Deviation;
import is.hi.hbv501g.group8.Persistence.Repositories.DeviationRepository;
import is.hi.hbv501g.group8.Services.DeviationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviationServiceImplementation implements DeviationService {
    private DeviationRepository deviationRepository;

    @Autowired
    public DeviationServiceImplementation(DeviationRepository deviationRepository) {
        this.deviationRepository = deviationRepository;
    }

    /**
     * @param deviation
     * @return
     */
    @Override
    public Deviation save(Deviation deviation) {
        return deviationRepository.save(deviation);
    }

    /**
     * @param deviation
     */
    @Override
    public void delete(Deviation deviation) {
        deviationRepository.delete(deviation);
    }

    /**
     * @return
     */
    @Override
    public List<Deviation> findAll() {
        return deviationRepository.findAll();
    }

    /**
     * @param ssn
     * @return
     */
    @Override
    public List<Deviation> findAllBySSN(String ssn) {
        return deviationRepository.findAllBySSN(ssn);
    }

    /**
     * @param type
     * @return
     */
    @Override
    public List<Deviation> findAllByType(Deviation.TYPE type) {
        return deviationRepository.findAllByType(type);
    }
}
