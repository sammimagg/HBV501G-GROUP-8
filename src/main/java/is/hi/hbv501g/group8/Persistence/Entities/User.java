/**
 * User
 *
 * Description: User object linked to DB.
 *              For authorizations and distinctify account types
 *              accountType 1: Admin
 *              accountType 2: Manager/Moderator
 *              accountType 3: Normal user/Employee
 *
 * @author kristófer Breki Gylfason - kbg15@hi.is
 * @author Halldór Jens Vilhjálsson - hjv6@hi.is
 * @author Samúel Magnússon - sam38@hi.is
 */

package is.hi.hbv501g.group8.Persistence.Entities;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User {

    @Id // Primary Key

    private String SSN;
    private String username;
    private String password;
    private int accountType;
    //driving log attribute


    @Transient
    private String employeeUserName;
    @Transient
    private int employeeAccountType;

    @Transient
    private String email;

    public void User() {

    }

    /**
     * Description: Setter for what type of account it will be, admin/employee
     *
     * @param accountType int
     */
    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    /**
     * Description: Setter for username of employee
     *
     * @param employeeUserName String
     */
    public void setEmployeeUserName(String employeeUserName) {
        this.employeeUserName = employeeUserName;
    }

    /**
     * Description: Setter for the employee type account
     *
     * @param employeeAccountType int
     */
    public void setEmployeeAccountType(int employeeAccountType) {
        this.employeeAccountType = employeeAccountType;
    }

    /**
     * Description: Getter for what type of account it will be, admin/employee
     *
     * @return accountType
     */
    public int getAccountType() {
        return accountType;
    }

    /**
     * Description: Getter for username of employee
     *
     * @return employeeUserName
     */
    public String getEmployeeUserName() {
        return employeeUserName;
    }

    /**
     * Description: Getter for what type of account it will be, admin/employee or user
     *
     * @return Admin, User
     */
    public String getEmployeeAccountType() {
        if(employeeAccountType == 0)
            return "Admin";
        else
            return "User";
    }

    /**
     * Description: Setter for SSN of user
     *
     * @param ssn String
     */
    public void setSSN(String ssn) {
        this.SSN = ssn;
    }

    /**
     * Description: Getter for SSN of user
     *
     * @return SSN
     */
    public String getSSN() {
        return SSN;
    }

    /**
     * Description: Getter for username of user
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Description: Setter for username of user
     *
     * @param username String
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Description: Getter for password of user
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Description: Setter for password of user
     *
     * @param password String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Description: Getter for which account type, admin/user
     *
     * @return accountType
     */
    public int getAccounttype() {
        return accountType;
    }

    /**
     * Description: Setter for which account type, admin/user
     *
     * @param accountType int
     */
    public void setAccounttype(int accountType) {
        this.accountType = accountType;
    }

    /**
     * Description: Getter for email of user/admin
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Description: Setter for email of user/admin
     *
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
