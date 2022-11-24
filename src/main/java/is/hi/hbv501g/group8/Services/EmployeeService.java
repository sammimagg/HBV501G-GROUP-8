package is.hi.hbv501g.group8.Services;

import is.hi.hbv501g.group8.Persistence.Entities.Deviation;
import is.hi.hbv501g.group8.Persistence.Entities.Employee;
import is.hi.hbv501g.group8.Persistence.Entities.Schedule;

import java.util.List;

public interface EmployeeService {
    Employee save(Employee employee);
    void delete(Employee employee);
    List<Employee> findAll();
    List<Employee> findByCompany(String company);
    Employee findBySSN(String ssn);

    interface DeviationService {
        Deviation save(Deviation deviation);

        void delete(Deviation deviation);

        Schedule save(Schedule schedule);
        void delete(Schedule schedule);
        List<Deviation> findAll();
        List<Deviation> findAllBySSN(String ssn);

        List<Deviation> findAllByType(Deviation.TYPE type);
    }
}
