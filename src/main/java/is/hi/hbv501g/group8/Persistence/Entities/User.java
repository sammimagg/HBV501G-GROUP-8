package is.hi.hbv501g.group8.Persistence.Entities;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    private String SSN;
    private String username;
    private String password;
    private int accounttype;

    public void User() {

    }

    public void setSSN(String ssn) {
        this.SSN = ssn;
    }

    @Id // Primary Key
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
        return accounttype;
    }

    public void setAccounttype(int accountType) {
        this.accounttype = accountType;
    }
}
