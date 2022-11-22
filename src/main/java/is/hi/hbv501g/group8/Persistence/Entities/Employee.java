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
    private String jobTitle;
    private double salary;
    @Column(name = "phone_number")
    private String phoneNumber;

    private LocalDate startDate;
    private int vacationDaysUsed, sickDaysUsed;
    @Transient
    private int remainingVacationDays, remainingSickDays;

    @Transient
    private String firstNameOfEmployee, lastNameOfEmployee, phoneNumberEmployee, ssnEmployee;


    public void Employee() {

    }

    public String getSsnEmployee() {
        return ssnEmployee;
    }

    public void setSsnEmployee(String ssnEmployee) {
        this.ssnEmployee = ssnEmployee;
    }

    public String getPhoneNumberEmployee() {
        System.out.println(phoneNumber);
        return phoneNumberEmployee;
    }

    public void setPhoneNumberEmployee(String phoneNumberEmployee) {
        this.phoneNumberEmployee = phoneNumberEmployee;
    }

    public String getFirstNameOfEmployee() {
        return firstNameOfEmployee;
    }

    public String getLastNameOfEmployee() {
        return lastNameOfEmployee;
    }

    public void setFirstNameOfEmployee(String firstNameOfEmployee) {

        this.firstNameOfEmployee = firstNameOfEmployee;
    }

    public void setLastNameOfEmployee(String lastNameOfEmployee) {
        this.lastNameOfEmployee = lastNameOfEmployee;
    }

    /**
     * Description:
     *
     * @return SSN
     */


    /**
     * Description:
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Description: Setter for first name of a employee
     * @param firstName First name of a employee
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Description: Getter for last name of a employee
     *
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getVacationDaysUsed() {
        return vacationDaysUsed;
    }

    public void setVacationDaysUsed(int vacationDaysUsed) {
        this.vacationDaysUsed = vacationDaysUsed;
    }

    public int getSickDaysUsed() {
        return sickDaysUsed;
    }

    public void setSickDaysUsed(int sickDaysUsed) {
        this.sickDaysUsed = sickDaysUsed;
    }

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
}
