package PAS;

import java.time.LocalDate;


public class Vehicle {

    private String carMake,carModel,carType,fuelType,carColor;
    int carYear;
    private double premiumCharged,purchasePrice;
    RatingEngine rateEngine = new RatingEngine();
    SQL sql = new SQL();
    SystemUserInput sui = new SystemUserInput();

    public Vehicle() {
    }

    public double getPremiumCharged() {
        return premiumCharged;
    }

    public void setData(int polID,int row) {
        String [][] arrayData;
        arrayData = sql.getTable(String.valueOf(polID), "vehicle","POL_NO");
        this.carMake = arrayData[row][2];
        this.carModel = arrayData[row][3];
        this.carYear = Integer.parseInt(arrayData[row][4]);
        this.carType = arrayData[row][5];
        this.fuelType = arrayData[row][6];
        this.purchasePrice=Double.parseDouble(arrayData[row][7]);
        this.carColor = arrayData[row][8];
        this.premiumCharged = Double.parseDouble(arrayData[row][9]);
    }



    public void create(LocalDate dateDL){
            try {
                this.carMake = sui.insertInputString(
                        "Enter make / brand: ");
                this.carModel = sui.insertInputString(
                        "Enter model: ");
                this.carYear = sui.insertInputInt(
                        "Enter year: ");
                this.carType = sui.insertInputString(
                        "Enter type: ");
                this.fuelType = sui.insertInputString(
                        "Enter fuel type: ");
                this.carColor = sui.insertInputString(
                        "Enter color: ");
                this.purchasePrice= roundOff(sui.insertInputDouble(
                        "Enter purchase price: "));
                this.premiumCharged = roundOff(rateEngine.rateVehicle(
                        this.purchasePrice, carYear, dateDL));
            } catch(Exception e){
                System.out.println("Error: "+e);
            }
    }


    Double roundOff(double doubNum){
        return  Math.round(doubNum * 100.0) / 100.0;
    }


    public String insertQuery(int polNo) {
        return "INSERT INTO VEHICLE (MAKE,MODEL,YEAR, VEHICLE_TYPE, FUEL_TYPE, PRICE, COLOR, PREMIUM, POL_NO) " +
                "values ('"+this.carMake +"', '"+this.carModel +"', '"+this.carYear +"','"+this.carType + "'," +
                "'"+this.fuelType+"','"+this.purchasePrice +"','"+this.carColor +
                "','"+this.premiumCharged +"','"+polNo +"')";
    }

    @Override
    public String toString() {
        return String.format("""
                       Car Make: %s
                      Car Color: %s
                      Car Model: %s
                       Car Year: %s
                       Car Type: %s
                      Fuel Type: %s
                 Purchase Price: %s
                Premium Charged: %s
                """,
                carMake,carColor,carModel,carYear,carType,fuelType,purchasePrice,premiumCharged);
    }
}
