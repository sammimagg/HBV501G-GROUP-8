package is.hi.hbv501g.group8.Persistence.Entities;

import java.time.LocalDateTime;
import java.util.List;

public class RealTimeInsightDAO {
    private Employee employee;
    private LocalDateTime clock_in_time;
    private boolean clocked_in;

    private Transaction last_transaction;

    public RealTimeInsightDAO(Employee employee, boolean clocked_in) {
        this.employee = employee;
        this.clocked_in = clocked_in;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDateTime getClock_in_time() {
        return clock_in_time;
    }

    public void setClock_in_time(LocalDateTime clock_in_time) {
        this.clock_in_time = clock_in_time;
    }

    public boolean isClocked_in() {
        return clocked_in;
    }

    public void setClocked_in(boolean clocked_in) {
        this.clocked_in = clocked_in;
    }

    public Transaction getLast_transaction() {
        return last_transaction;
    }

    public void setLast_transaction(Transaction last_transaction) {
        this.last_transaction = last_transaction;
    }
}


/*

    public List<Employee> getEmployeeList() {
        List<Employee> allEmployees;
        allEmployees = employeeService.findAll();

        for(Employee row : allEmployees) {
            row.setFirstNameOfEmployee(row.getFirstName());
            row.setLastNameOfEmployee(row.getLastName());
            row.setPhoneNumberEmployee(row.getPhoneNumber());
            row.setSsnEmployee(row.getSSN());
            row.setEmployeeUserName(row.getUsername());
            if(transactionService.findBySSNAndFinished(row.getSSN(), false) == null ){
                row.setStatus("offDuty");
            }
            else {
                row.setStatus("onDuty");
            }
        }
        return allEmployees;

 */