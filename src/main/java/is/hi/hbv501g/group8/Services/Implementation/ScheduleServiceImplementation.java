package is.hi.hbv501g.group8.Services.Implementation;


import is.hi.hbv501g.group8.Persistence.Entities.Schedule;
import is.hi.hbv501g.group8.Persistence.Repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImplementation implements ScheduleService {
    private ScheduleRepository scheduleRepository;
    @Autowired
    public ScheduleServiceImplementation(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public void delete(Schedule schedule) {
        scheduleRepository.delete(schedule);
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public List<Schedule> findAllBySSN(String ssn) {
        return scheduleRepository.findAllBySSN(ssn);
    }
}
