package is.hi.hbv501g.group8.Persistence.Repositories;

import is.hi.hbv501g.group8.Persistence.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee save(Employee employee);
    void delete(Employee employee);
    List<Employee> findAll();
    List<Employee> findByCompany(String company);
    Employee findBySSN(String ssn);
}
