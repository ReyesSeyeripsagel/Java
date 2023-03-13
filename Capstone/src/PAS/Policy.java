package PAS;

import java.time.LocalDate;
import java.util.*;

class Policy {
    private int accID,polID,numOfVehicle ;
    private LocalDate effDate,expDate;
    private String polStatus;
    double polPremium;
    SystemUserInput sui = new SystemUserInput();
    String [][] arrayData = new String[0][5];
    List<Vehicle> vehiclesArray = new ArrayList<>();
    Vehicle vehicle;
    CustomerAccount acc = new CustomerAccount();
    PolicyHolder polHolder = new PolicyHolder();
    SQL sql = new SQL();

    public int getPolID() {
        return polID;
    }

    

    public LocalDate getEffDate() {
        return effDate;
    }



    public LocalDate getExpDate() {
        return expDate;
    }



    public void setData(int polID) {
        this.polID =polID;
        this.arrayData = sql.getTable(String.valueOf(polID),"POLICY","POL_NO");
        this.polID = Integer.parseInt(this.arrayData[0][0]);
        this.accID = Integer.parseInt(this.arrayData[0][1]);
        this.effDate = LocalDate.parse(this.arrayData[0][2]);
        this.expDate = LocalDate.parse(this.arrayData[0][3]);
        this.polPremium = Double.parseDouble(this.arrayData[0][4]);
        polHolder.setData(polID);
        this.numOfVehicle = sql.countRow(String.valueOf(polID), "VEHICLE", "POL_NO");
        for (int i = 0; i < numOfVehicle; i++) {
            vehicle = new Vehicle();
            vehicle.setData(polID, i);
            vehiclesArray.add(vehicle);
        }
    }

    public void create(int accID) {
        acc.setData(accID);
        this.accID=accID;
        if (sql.checkTable("POLICY")) {
            this.polID = 220000;
        }else{
            this.polID = sql.generateIntID("POLICY");
        }
        System.out.println("Policy ID: "+ polID);
        this.effDate = sui.insertInputDate(
                "Enter Policy Effective Date (YYYY-MM-DD): ");
        this.expDate = effDate.plusMonths(6);
        if(sui.checkIfYes("Is the policy holder same as the account named? Y(es) " +
                "")){
            polHolder.create(acc.getfName(), acc.getlName(), acc.getAddress());
        }else {
            polHolder.create();
        }
        numOfVehicle = sui.insertInputInt("Enter number of vehicle(s) to be added: ");

        for (int i = 0; i < numOfVehicle; i++) {
            Vehicle vehicle = new Vehicle();
            System.out.println("Input vehicle information");
            vehicle.create(polHolder.getDateDL());
            vehiclesArray.add(vehicle);
            this.polPremium += vehicle.getPremiumCharged();
        }
        int menu;
        do {
            sui.clearScreen();
            System.out.print("""
                                 Policy Menu
            ┌──────────────────────── >o< ────────────────────────┐
            │ 1. Check policy information.                        │
            │ 2. Check policy holder information.                 │
            │ 3. Scroll through vehicle information.              │
            │ 4. Buy policy.                                      │
            │ 5. Exit/Cancel Policy.                              │
            └─────────────────────────────────────────────────────┘
            """);
            menu = sui.insertInputInt("Enter menu: ");
            switch (menu) {
                    case 4 -> {
                        sql.insertQuery(insertQuery());
                        int polNo = Integer.parseInt(sql.getID("POLICY", "ORDER BY POL_NO DESC LIMIT 1"));
                        sql.insertQuery(polHolder.insertQuery(polNo));
                        for (int i = 0; i < numOfVehicle; i++) {
                            sql.insertQuery(vehiclesArray.get(i).insertQuery(polNo));
                        }
                        menu=5;
                        System.out.println("Policy Purchased!");
                    }
                    case 1, 2, 3 -> {
                        sui.clearScreen();
                        polHolderVehicleInfo(menu);
                    }
                    case 5 -> System.out.println("Exiting...");
                    default -> {
                        System.out.println("Value is not in the choices.");
                        sui.enterKey();
                    }
            }
        } while (menu!=5);
    }

    public void polHolderVehicleInfo (int menu){
        switch (menu) {
            case 1:
                if (expDate.isEqual(effDate.plusMonths(6))){
                    if (effDate.isAfter(LocalDate.now())) {
                        polStatus = "INACTIVE";
                    } else if (expDate.isEqual(LocalDate.now())||expDate.isBefore(LocalDate.now())){
                        polStatus = "EXPIRED";
                    } else if (effDate.isEqual(LocalDate.now())||effDate.isBefore(LocalDate.now())){
                        polStatus = "ACTIVE";
                    }
                }else{
                    polStatus = "CANCELLED";
                }
                System.out.println(this);
                sui.enterKey();
                break;
            case 2:
                System.out.print("""
                        ┌──────────────────────── >o< ────────────────────────┐
                        │              Policy holder information              │
                        └─────────────────────────────────────────────────────┘
                        """);
                System.out.println(polHolder.toString());
                sui.enterKey();
                break;
            case 3:
                
                for (int i = 0; i < numOfVehicle; i++) {
                    System.out.print("""
                        ┌──────────────────────── >o< ────────────────────────┐
                        │                       Vehicle                       │
                        └─────────────────────────────────────────────────────┘
                        """);
                    int vehicleNum = i+1;
                    System.out.println("Vehicle information number: " + vehicleNum + " ");
                    System.out.println(vehiclesArray.get(i).toString());
                    sui.enterKey();
                    sui.clearScreen();
                }
                break;
        }

    }

    void cancelPolicy(int polID){
        try {
            System.out.println(expDate);
            sql.updateTable(String.valueOf(polID), "POLICY", 
            "POL_NO","POL_EXP_DATE",LocalDate.now().toString());
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public boolean checkPol() {
        try{
            this.polID = sui.insertInputInt(
                    "Enter policy no.: ");
            if (sql.checkID(String.valueOf(polID), "policy")){
                return true;
            }
        }catch (Exception e){
            System.out.println("Error: "+ e);
        }
        return false;
    }
    public String insertQuery() {
        return "INSERT INTO POLICY (POL_NO, POL_EFF_DATE, POL_EXP_DATE, PREMIUM,ACCOUNT_ID)  " +
                "values ('"+this.polID +"','"+this.effDate +"', '"+this.expDate +"','"+this.polPremium + "'," +
                "'"+accID+"')";
    }
    @Override
    public String toString() {
        return String.format("""
                        ┌──────────────────────── >o< ────────────────────────┐
                        │                  Policy Information                 │
                        └─────────────────────────────────────────────────────┘
                        Policy ID: %s
                        Policy holder name: %s
                        Policy effective date:%s
                        Policy expiration date:%s
                        Number of vehicle(s):%s
                        Policy premium: %s
                        Policy status: %s""",
                polID,polHolder.getfName()+" "+ polHolder.getlName(),
                effDate,expDate, this.numOfVehicle,polPremium,polStatus);
    }
}
