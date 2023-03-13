package PAS;

import java.util.Scanner;

class CustomerAccount {
    SystemUserInput sui = new SystemUserInput();
    String [][] arrayData = new String[0][3];
    private int accountID;
    private String fName,lName,address;
    SQL sql = new SQL();
    public int getAccountID() {
        return accountID;
    }
    public String getfName() {
        return fName;
    }
    public String getlName() {
        return lName;
    }
    public String getAddress() {
        return address;
    }
    public String searchData() {
        this.fName = sui.insertInputString(
                "Enter first name: ");
        this.lName = sui.insertInputString(
                "Enter last name: ");
        try {
            int ID = Integer.parseInt(sql.getID("ACCOUNT", "WHERE FIRST_NAME = '" + fName
                    + "' AND LAST_NAME = '" + lName + "';"));
            setData(ID);
            return toString();
        }catch (Exception e){
            return "No equivalent ID in the system.";
        }

    }

    public void setData(int accID) { //Set data from SQL
        this.accountID =accID;
        this.arrayData = sql.getTable(String.valueOf(accountID), "account","ACCOUNT_ID");
        this.accountID = Integer.parseInt(this.arrayData[0][0]);
        this.fName = this.arrayData[0][1];
        this.lName = this.arrayData[0][2];
        this.address = this.arrayData[0][3];
    }

    void create(){ //create view modify account if have time
        
        try {
            System.out.print("""
                ┌──────────────────────── >o< ────────────────────────┐
                │                  Account Creation                   │
                └─────────────────────────────────────────────────────┘
                """);
            this.fName = sui.insertInputString(
                    "Enter First Name: ");
            this.lName = sui.insertInputString(
                    "Enter Last Name: ");
            this.address = sui.insertInputString(
                    "Enter Address: ");
            if (sui.checkIfYes("Are the details above correct? Y(es) ")) {
                sui.clearScreen();
                if (sql.checkTable("ACCOUNT")) {
                    this.accountID = 2200;
                }else{
                    this.accountID = sql.generateIntID("ACCOUNT");
                }


                System.out.print("""
                ┌──────────────────────── >o< ────────────────────────┐
                │                   Account Created                   │
                └─────────────────────────────────────────────────────┘
                """);
                System.out.println(this);
                sql.insertQuery(insertQuery());
            } else{
                System.out.println("Account is not created!");
            }
        } catch(Exception e){
            System.out.println("Error: "+e);
        }
    }

    public boolean checkAcc() {
        try{
            this.accountID = sui.insertInputInt(
                    "Enter account no.: ");
            return sql.checkID(String.valueOf(this.accountID),"account");
        }catch(Exception e) {
            System.out.println("Error: "+ e);
        }
        return false;
    }

    public String toString() {
        return "ID: "+ this.accountID +"\nName: "+this.fName + " " + this.lName + "\nAddress: " + this.address;
    }

    public String insertQuery() {
        return "INSERT INTO ACCOUNT (ACCOUNT_ID, FIRST_NAME, LAST_NAME, ADDRESS)  " +
                "values ('"+this.accountID +"','"+this.fName +"', '"+this.lName +"','"+this.address +"')";
    }

}
