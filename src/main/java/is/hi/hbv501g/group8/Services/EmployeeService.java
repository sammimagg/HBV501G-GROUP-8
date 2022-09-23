package is.hi.hbv501g.group8.Services;

import is.hi.hbv501g.group8.Persistence.Entities.Employee;

import java.util.List;

public interface EmployeeService {
    Employee save(Employee employee);
    void delete(Employee employee);
    List<Employee> findAll();
    List<Employee> findByCompany(String company);
    Employee findBySSN(String ssn);
}
