package is.hi.hbv501g.group8.Persistence.Entities;

import org.springframework.lang.Nullable;

import java.time.LocalDate;

public class TransHelper {
    private String ssn;
    @Nullable
    private LocalDate dateFrom;
    @Nullable
    private LocalDate dateTo;

    public TransHelper(String ssn, @Nullable LocalDate dateFrom, @Nullable LocalDate dateTo) {
        this.ssn = ssn;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }
}
