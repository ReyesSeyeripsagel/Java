package PAS;

import java.time.LocalDate;

public class PolicyHolder {
    private String fName,lName,address,numDL;
    private LocalDate dateOfBirth,dateDL;
    SQL sql = new SQL();
    SystemUserInput sui = new SystemUserInput();
    public PolicyHolder() {
    }

    public LocalDate getDateDL() {
        return dateDL;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public void setData(int polID) {
        String[][] arrayData = sql.getTable(String.valueOf(polID), "POLICY_HOLDER", "POL_NO");
        this.fName = arrayData[0][2];
        this.lName = arrayData[0][3];
        this.address = arrayData[0][4];
        this.numDL = arrayData[0][5];
        this.dateDL = LocalDate.parse(arrayData[0][6]);
        this.dateOfBirth = LocalDate.parse(arrayData[0][7]);
    }

    public void create(){
        this.fName = sui.insertInputString(
                "Enter First Name: ");
        this.lName = sui.insertInputString(
                "Enter Last Name: ");
        this.address = sui.insertInputString(
                "Enter Address: ");
        create(fName, lName, address);
    }
    public void create(String fName,String lName,String address){
        this.fName=fName;
        this.lName=lName;
        this.address=address;
        this.dateOfBirth = sui.insertInputDate(
                "Enter Date of Birth. (YYYY-MM-DD): ");

        this.numDL = sui.insertInputString(
                "Enter Driver\'s license number.: ");
                
        this.dateDL = sui.insertInputDate(
                "Enter date of driver\'s license was first issued. \n(YYYY-MM-DD): ");
        clearScreen();
        System.out.print("""
                    ┌──────────────────────── >o< ────────────────────────┐
                    │               Policy holder details                 │
                    └─────────────────────────────────────────────────────┘
                    """);
        System.out.println(this);
    }


    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public String toString() {
        return  "Name: " +this.fName + " " + this.lName + " \n" +
                "Date of birth: " +this.dateOfBirth  +" \n" +"Address: " +this.address +" \n" +
                "Driver\'s license: " +this.numDL  +" \n" + "Date Issued: " +this.dateDL;
    }

    public String insertQuery(int polNo) {
        return "INSERT INTO POLICY_HOLDER (FIRST_NAME, LAST_NAME, ADDRESS,DL_NO,DL_DATE,POL_NO,BIRTH_DATE)  " +
                "values ('"+this.fName +"', '"+this.lName +"','"+this.address +"', '"+
                this.numDL +"', '"+this.dateDL+"', '"+polNo +"', '"+dateOfBirth +"')";
    }
}
