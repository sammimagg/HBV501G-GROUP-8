/**
 * Employee
 *
 * Description: Employee object linked to DB.
 *              Contains relevant information about employee.
 *
 * @author kristófer Breki Gylfason - kbg15@hi.is
 * @author Halldór Jens Vilhjálsson - hjv6@hi.is
 * @author Samúel Magnússon - sam38@hi.is
 */
package is.hi.hbv501g.group8.Persistence.Entities;

import is.hi.hbv501g.group8.Utilities.Status;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Entity
@Table(name = "employees")
public class Employee extends User {

    private String firstName;
    private String lastName;
    private String company;
    private String email;
    private String jobTitle;
    private double salary;

    @Column(name = "phone_number")
    private String phoneNumber;

    private LocalDate startDate;
    private int vacationDaysUsed, sickDaysUsed;
    @Transient
    private int remainingVacationDays, remainingSickDays;

    @Transient
    private String firstNameOfEmployee, lastNameOfEmployee, phoneNumberEmployee, ssnEmployee, status;



    public void Employee() {

    }

    /**
     * Description: Getter for Ssn for employee
     *
     * @return ssnEmployee
     */
    public String getSsnEmployee() {
        return ssnEmployee;
    }

    /**
     * Description: Setter for Ssn for employee
     *
     * @param ssnEmployee String
     */
    public void setSsnEmployee(String ssnEmployee) {
        this.ssnEmployee = ssnEmployee;
    }

    /**
     * Description: Getter for employee phone number
     *
     * @return phoneNumberEmployee
     */
    public String getPhoneNumberEmployee() {
        System.out.println(phoneNumber);
        return phoneNumberEmployee;
    }

    /**
     * Description: Getter for emails
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Description: Setter for emails
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Description: Setter for employee phone number
     *
     * @param phoneNumberEmployee String
     */
    public void setPhoneNumberEmployee(String phoneNumberEmployee) {
        this.phoneNumberEmployee = phoneNumberEmployee;
    }

    /**
     * Description: Getter for firstname of employee
     *
     * @return firstNameOfEmployee
     */
    public String getFirstNameOfEmployee() {
        return firstNameOfEmployee;
    }

    /**
     * Description: Getter for lastname of employee
     *
     * @return lastNameOfEmployee
     */
    public String getLastNameOfEmployee() {
        return lastNameOfEmployee;
    }

    /**
     * Description: Setter for firstNameOfEmployee
     *
     * @param firstNameOfEmployee String
     */
    public void setFirstNameOfEmployee(String firstNameOfEmployee) {
        this.firstNameOfEmployee = firstNameOfEmployee;
    }

    /**
     * Description: Setter for lastNameOfEmployee
     *
     * @param lastNameOfEmployee String
     */
    public void setLastNameOfEmployee(String lastNameOfEmployee) {
        this.lastNameOfEmployee = lastNameOfEmployee;
    }

    /**
     * Description: Getter for firstname "admin"
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Description: Setter for firstname of an "admin"
     *
     * @param firstName firstname of an "admin"
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Description: Getter for lastname of an "admin"
     *
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Description: Setter for lastname of an "admin"
     *
     * @param lastName first name of an "admin"
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Description: Getter for getting company of employee
     *
     * @return company
     */
    public String getCompany() {
        return company;
    }

    /**
     * Description: Setter for setting company of employee
     *
     * @param company String
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Description: Getter for job title of an employee
     *
     * @return jobTitle
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Description: Setter for job title of an employee
     *
     * @param jobTitle String
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * Description: Getter for salary of an employee
     *
     * @return salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Description: Setter for salary of an employee
     *
     * @param salary double
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Description: Getter for phone number of an "admin"
     *
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Description: Setter for phone number of an "admin"
     *
     * @param phoneNumber String
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Description: Getter for start date of an employee
     *
     * @return startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Description: Setter for start date of an employee
     *
     * @param startDate LocalDate
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Description: Getter for vacation days used by employee/admin
     *
     * @return vacationDaysUsed
     */
    public int getVacationDaysUsed() {
        return vacationDaysUsed;
    }

    /**
     * Description: Setter for vaction days used by employee/admin
     *
     * @param vacationDaysUsed int
     */
    public void setVacationDaysUsed(int vacationDaysUsed) {
        this.vacationDaysUsed = vacationDaysUsed;
    }

    /**
     * Description: Getter for sick days used by employee/admin
     *
     * @return sickDaysUsed
     */
    public int getSickDaysUsed() {
        return sickDaysUsed;
    }

    /**
     * Description: Setter for sick days used by employee/admin
     *
     * @param sickDaysUsed int
     */
    public void setSickDaysUsed(int sickDaysUsed) {
        this.sickDaysUsed = sickDaysUsed;
    }

    /**
     * Description: Getter for remaining vacation days of the month
     *
     * @return remainingVacationDays
     */
    public int getRemainingVacationDays() {
        return remainingVacationDays;
    }

    /**
     * Updates remainingVacationDays with:
     * 2 x Months Worked - Vacation Days Used
     */
    public void updateRemainingVacationDays() {
        this.remainingVacationDays = (int)(ChronoUnit.MONTHS.between(getStartDate(),LocalDate.now())*2)-(getVacationDaysUsed());
    }

    /**
     * Description: Getter for remaining sick days of the month
     *
     * @return remainingSickDays
     */
    public int getRemainingSickDays() {
        return remainingSickDays;
    }

    /**
     * Updates remainingSickDays with:
     * 2 x Months Worked - Sick Days Used
     */

    public void updateRemainingSickDays() {
        this.remainingSickDays = (int)(ChronoUnit.MONTHS.between(getStartDate(),LocalDate.now())*2)-(getSickDaysUsed());;
    }

    /**
     * Description: Getter for status of user
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Description: Setter for status of user
     *
     * @param status String
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
