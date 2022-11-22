package is.hi.hbv501g.group8.Persistence.Entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "drivinglog")
public class Driving {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private String licencePlate, SSN;
    private int odometerStart, odometerEnd;
    private LocalDate dags;
    @Transient
    private int distanceDriven;

    public LocalDate getDags() {
        return dags;
    }

    public void setDags(LocalDate dags) {
        this.dags = dags;
    }

    public Driving() {
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public int getOdometerStart() {
        return odometerStart;
    }

    public void setOdometerStart(int odometerStart) {
        this.odometerStart = odometerStart;
    }

    public int getOdometerEnd() {
        return odometerEnd;
    }

    public void setOdometerEnd(int odometerEnd) {
        this.odometerEnd = odometerEnd;
    }

    public int getDistanceDriven() {
        return odometerEnd - odometerStart;
    }
}
