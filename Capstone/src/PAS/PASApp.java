package PAS;

import java.time.LocalDate;

class PASApp {
    public static void main(String[] args) throws InterruptedException {
        
        SystemUserInput sui = new SystemUserInput();
        CustomerAccount acc ;
        Policy pol;
        Claim claim;

        sui.clearScreen();
        System.out.println(sui);
        for (int i = 0; i < 52; i++) {
            System.out.print("──");
            Thread.sleep(10);
        }
        do {
            acc = new CustomerAccount();
            pol = new Policy();
            sui.clearScreen();
            claim = new Claim();
            System.out.print("""
                                               Main Menu
                        ┌──────────────────────── >o< ────────────────────────┐
                        │ 1. Create a new customer account                    │
                        │ 2. Get a policy quote and buy the policy            │
                        │ 3. Cancel a specific policy                         │
                        │ 4. File an accident claim against a policy          │
                        │ 5. Search for a customer account                    │
                        │ 6. Search for and display a specific policy         │
                        │ 7. Search for and display a specific claim          │
                        │ 8. Exit the PAS system                              │
                        └─────────────────────────────────────────────────────┘
                        """);

            switch (sui.insertInputInt("Enter menu: ")) {
                case 1 -> {//Good
                    sui.clearScreen();
                    acc.create();
                    sui.enterKey();
                }
                case 2 -> {//Good
                    if (acc.checkAcc()) {
                        sui.clearScreen();
                        System.out.print("""
                            ┌──────────────────────── >o< ────────────────────────┐
                            │                    Policy Creation                  │
                            └─────────────────────────────────────────────────────┘
                            """);
                        pol.create(acc.getAccountID());
                    } else {
                        System.out.println("Account doesn't exist!");
                    }
                    sui.enterKey();
                }
                case 3 -> {//GOOD
                    
                    if(pol.checkPol()) {
                        pol.setData(pol.getPolID());
                        if (pol.getExpDate().isEqual(pol.getEffDate().plusMonths(6))){
                            if (pol.getExpDate().isEqual(LocalDate.now())||pol.getExpDate().isBefore(LocalDate.now())){
                                System.out.println("Policy is already expired.");
                            }else{
                                sui.clearScreen();
                                System.out.print("""
                            ┌──────────────────────── >o< ────────────────────────┐
                            │                  Cancelling Policy                  │
                            └─────────────────────────────────────────────────────┘
                            """);
                                System.out.println("Policy No." + pol.getPolID());
                                if (sui.checkIfYes("Enter Y(es) to confirm ")) {
                                    pol.cancelPolicy(pol.getPolID());
                                    System.out.println("Policy is cancelled");
                                }else{
                                    System.out.println("Policy is not cancelled");
                                }
                            }
                        }else{
                            System.out.println("Policy already cancelled.");
                        }
                    }else {
                        System.out.println("Policy does not exist");
                    }
                    sui.enterKey();
                }
                case 4 -> {//File a claim
                    if (pol.checkPol()) {
                        sui.clearScreen();
                        System.out.print("""
                    ┌──────────────────────── >o< ────────────────────────┐
                    │                        Claim                        │
                    └─────────────────────────────────────────────────────┘
                    """);
                        System.out.println(claim.Create(pol.getPolID()));
                    }
                    sui.enterKey();
                }
                case 5 -> {//Search customer
                    sui.clearScreen();
                    System.out.print("""
                    ┌──────────────────────── >o< ────────────────────────┐
                    │               Search Customer Account               │
                    └─────────────────────────────────────────────────────┘
                    """);
                    System.out.println(acc.searchData());
                    sui.enterKey();
                }
                case 6 -> {//Good
                    if (pol.checkPol()) {
                        pol.setData(pol.getPolID());
                        int option;
                        do {
                            sui.clearScreen();
                            System.out.print("""
                                                 Policy Menu
                            ┌──────────────────────── >o< ────────────────────────┐
                            │ 1. Check policy information                         │
                            │ 2. Check policy holder information                  │
                            │ 3. Scroll through vehicle information               │
                            │ 4. Get claim(s)                                     │
                            │ 5. Exit                                             │
                            └─────────────────────────────────────────────────────┘
                            """);
                            option = sui.insertInputInt("Enter menu: ");
                            sui.clearScreen();
                            switch (option) {
                                case 1, 2, 3 -> pol.polHolderVehicleInfo(option);
                                case 4 ->{
                                    System.out.print("""
                                    ┌──────────────────────── >o< ────────────────────────┐
                                    │                  Claim Information                  │
                                    └─────────────────────────────────────────────────────┘
                                    """);
                                    claim.getClaimIds(pol.getPolID());
                                    sui.enterKey();
                                }
                                case 5 -> System.out.println("Exiting...");
                                default -> System.out.println("Number is not in the choices");
                            }
                        } while (option != 5);
                    } else {
                        System.out.println("Policy does not exist");
                        sui.enterKey();
                    }
                }
                case 7 -> {//Not good
                    if (claim.checkClaim()) {
                        sui.clearScreen();
                        System.out.print("""
                        ┌──────────────────────── >o< ────────────────────────┐
                        │                  Claim Information                  │
                        └─────────────────────────────────────────────────────┘
                        """);
                        claim.setData(claim.getClaimID());
                        System.out.println(claim);
                    } else {
                        System.out.println("Claim does not exist");
                    }
                    sui.enterKey();
                }
                case 8 -> {
                    sui.clearScreen();
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> {
                    System.out.println("Wrong Input");
                    sui.enterKey();

                }

            }
            
        } while (true);
    }
}



