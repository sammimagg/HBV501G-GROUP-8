package is.hi.hbv501g.group8.Persistence.Entities;

public class SessionUser {

    private String ssn;
    private String authToken;

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
