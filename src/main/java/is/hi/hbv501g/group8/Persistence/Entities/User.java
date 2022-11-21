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

    public void User() {

    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public void setEmployeeUserName(String employeeUserName) {
        this.employeeUserName = employeeUserName;
    }

    public void setEmployeeAccountType(int employeeAccountType) {
        this.employeeAccountType = employeeAccountType;
    }

    public int getAccountType() {
        return accountType;
    }

    public String getEmployeeUserName() {
        return employeeUserName;
    }

    public String getEmployeeAccountType() {
        if(employeeAccountType == 0)
            return "Admin";
        else
            return "User";
    }

    public void setSSN(String ssn) {
        this.SSN = ssn;
    }

    public String getSSN() {
        return SSN;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccounttype() {
        return accountType;
    }

    public void setAccounttype(int accountType) {
        this.accountType = accountType;
    }
}
