package PAS;

import java.time.LocalDate;
import java.util.Arrays;

public class Claim {

    private String claimID,accidentAddress,accidentDescription,damageDescription;
    private int polID;
    private LocalDate accidentDate;
    private Double estimatedCost;
    SystemUserInput sui = new SystemUserInput();
    SQL sql = new SQL();

    public String getClaimID() {
        return claimID;
    }

    String Create(int polID) {
        this.polID = polID;
        if (sql.checkTable("CLAIM")) {
            this.claimID = "C22001";
        }else{
            this.claimID = generateClaimID();;
        }
        System.out.println("Claim ID : "+claimID);
        this.accidentDate = sui.insertInputDate(
                "Enter date of accident(YYYY-MM-DD): ");
        this.accidentAddress = sui.insertInputString(
                "Enter address where accident happened: ");
        this.accidentDescription = sui.insertInputString(
                "Description of accident: ");
        this.damageDescription = sui.insertInputString(
                "Description of damage to vehicle: ");
        this.estimatedCost = sui.insertInputDouble(
                "Estimated cost of repairs: ");
        if (sui.checkIfYes("Enter Y(es) to Confirm ")) {
            sql.insertQuery(insertQuery());
            return "Claim is created!";
        }
        return "Claim is not created!";
    }


    public void setData(String claimID) {
        String[][] arrayData = sql.getTable(claimID, "CLAIM", "CLAIM_ID");
        this.claimID = arrayData[0][0];
        this.accidentDate = LocalDate.parse(arrayData[0][1]);
        this.accidentAddress = arrayData[0][2];
        this.accidentDescription = arrayData[0][3];
        this.damageDescription = arrayData[0][4];
        this.estimatedCost = Double.parseDouble(arrayData[0][5]);
        this.polID = Integer.parseInt(arrayData[0][6]);
    }

    public boolean checkClaim() {
            this.claimID = sui.insertInputString(
                    "Enter claim no.: ");
            return sql.checkID(claimID, "claim");
    }

    String generateClaimID(){
        claimID=sql.getID("CLAIM", "ORDER BY 1 DESC LIMIT 1");
        String[] part = claimID.split("(?<=\\D)(?=\\d)");
        int incr = Integer.parseInt(part[1])+1;
        return part[0] + incr;
    }

    public void getClaimIds(int polID){
        String [][] arrayData;
        arrayData = sql.getTable(String.valueOf(polID), "CLAIM", "POL_ID");
        if (arrayData.length!=0) {
            for (int index = 0; index < arrayData.length; index++) {
                System.out.println("ID: "+arrayData[index][0]+"\t Claim date: "+arrayData[index][1]);
            }
        } else {
            System.out.println("NO CLAIMS ATTACHED");
        }
        
        //System.out.println(Arrays.deepToString(arrayData));
    }



    public String insertQuery() {
        return "INSERT INTO CLAIM (CLAIM_ID, ACCIDENT_DATE, ADDRESS,ACCIDENT_DESC,DAMAGE_DESC,REPAIR_COST,POL_ID)  " +
                "values ('"+this.claimID +"', '"+this.accidentDate +"','"+this.accidentAddress +"', '"+
                this.accidentDescription +"', '"+this.damageDescription+"', '"+this.estimatedCost +"', '"+this.polID +"')";
    }

    @Override
    public String toString() {
            return String.format("""
                        Claim ID: %s
                        Address of the accident: %s
                        Accident description: %s
                        Vehicle damage description: %s
                        Date of the Accident: %s
                        Estimated cost of repairs: %s""",
                    this.claimID,this.accidentAddress,this.accidentDescription,this.damageDescription,
            this.accidentDate,this.estimatedCost);
    }
}
