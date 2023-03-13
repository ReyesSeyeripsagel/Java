package PAS;

import java.time.LocalDate;
import java.time.Period;

public class RatingEngine {
    public double rateVehicle(double purchasePrice, int carYear, LocalDate lastDLIssue){
        Period period = Period.between(lastDLIssue,LocalDate.now());
        double yearMinus = period.getYears();
        int carAge = LocalDate.now().getYear() - carYear;
        double vpf = (carAge<1?.01:(carAge<3?.008:(carAge<5?.007:
                (carAge<10?.006:(carAge<15?.004:(carAge<20?.002:carAge<40?.001:0))))));
        return (purchasePrice * vpf) + ((purchasePrice/100)/yearMinus);
    }
}
