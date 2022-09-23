package is.hi.hbv501g.group8.Services.Implementation;

import is.hi.hbv501g.group8.Persistence.Entities.Employee;
import is.hi.hbv501g.group8.Persistence.Repositories.EmployeeRepository;
import is.hi.hbv501g.group8.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImplementation implements EmployeeService {
    private EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeServiceImplementation(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> findByCompany(String company) {
        return employeeRepository.findByCompany(company);
    }

    @Override
    public Employee findBySSN(String ssn) {
        return employeeRepository.findBySSN(ssn);
    }
}
