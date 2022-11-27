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


    /**
     * Description:
     *
     * Doesn't do anything for now
     */
    public LocalDate getDags() {
        return dags;
    }

    /**
     * Description:
     *
     * Doesn't do anything for now
     */
    public void setDags(LocalDate dags) {
        this.dags = dags;
    }

    public Driving() {
    }

    /**
     * Description: Getter for DrivingLog ID
     *
     * Doesn't do anything for now
     */
    public long getID() {
        return ID;
    }

    /**
     * Description: Setter for DrivingLog ID
     *
     * @param ID
     */
    public void setID(long ID) {
        this.ID = ID;
    }

    /**
     * Description: Getter for DrivingLog LicencePlate
     *
     * @return licencePlate
     */
    public String getLicencePlate() {
        return licencePlate;
    }

    /**
     * Description: Setter for DrivingLog LicencePlate
     *
     * @param licencePlate
     */
    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    /**
     * Description: Getter for DrivingLog SSN
     *
     * @return SSN
     */
    public String getSSN() {
        return SSN;
    }

    /**
     * Description: Setter for DrivingLog SSN
     *
     * @param SSN
     */
    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    /**
     * Description: Getter for OdometerStart
     *
     * @return odometerStart
     */
    public int getOdometerStart() {
        return odometerStart;
    }

    /**
     * Description: Setter for OdometerStart
     *
     * @param odometerStart
     */
    public void setOdometerStart(int odometerStart) {
        this.odometerStart = odometerStart;
    }

    /**
     * Description: Getter for OdometerEnd
     *
     * @return odometerEnd
     */
    public int getOdometerEnd() {
        return odometerEnd;
    }

    /**
     * Description: Setter for OdometerEnd
     *
     * @param odometerEnd
     */
    public void setOdometerEnd(int odometerEnd) {
        this.odometerEnd = odometerEnd;
    }

    /**
     * Description: Get the disctance driven from start to end
     *
     * @return getDistanceDriven, End-Start
     */
    public int getDistanceDriven() {
        return odometerEnd - odometerStart;
    }
}
