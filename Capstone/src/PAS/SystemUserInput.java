package PAS;

import java.time.LocalDate;
import java.util.Scanner;

public class SystemUserInput {
    
    Scanner scan = new Scanner(System.in);


    Integer insertInputInt (String msg){

        while(true) {

            int intInput;
            System.out.print(msg);

            try {

                intInput = Integer.parseInt(scan.nextLine());
                return intInput;

            }catch (Exception e){
                System.out.println("The value must be a number. Try again!");
            }
            //nosuchelementexception
        }
    }

    Double insertInputDouble (String msg){

        while(true) {

            double doubleInput;
            System.out.print(msg);

            try {

                doubleInput = Double.parseDouble(scan.nextLine());
                return doubleInput;

            } catch (Exception e) {
                System.out.println("The value must be a number. Try again!");
            }
            //nosuchelementexception
        }
    }

    String insertInputString (String msg){

        while(true) {
            System.out.print(msg);

            try {
                return scan.nextLine();

            } catch (Exception e) {
                System.out.println("The value you entered is not valid. Try again!");
            }
            //nosuchelementexception

        }

    }

    LocalDate insertInputDate(String MSG){

        while(true) {
            
            LocalDate dateInput;
            System.out.print(MSG);

            try {

                dateInput = LocalDate.parse(scan.nextLine());

                return dateInput;

            } catch (Exception e) {
                System.out.println("The value you entered is not valid. Try again!");
            }
            //nosuchelementexception

        }
    }

    Boolean checkIfYes(String MSG) {

        String flag;
        System.out.print(MSG);

        try {
            flag = scan.nextLine();
            if (flag.equalsIgnoreCase("y") || flag.equalsIgnoreCase("yes")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }
    void enterKey(){
        System.out.print("Press \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    public String toString() {
        return """
                 ____    ___   ____   ____  ___ ___   ____         __   ____  ____    _____ ______   ___   ____     ___\s
                |    \\  /   \\ |    \\ |    ||   |   | /    |       /  ] /    ||    \\  / ___/|      | /   \\ |    \\   /  _]
                |  _  ||     ||  D  ) |  | | _   _ ||  o  |      /  / |  o  ||  o  )(   \\_ |      ||     ||  _  | /  [_\s
                |  |  ||  O  ||    /  |  | |  \\_/  ||     |     /  /  |     ||   _/  \\__  ||_|  |_||  O  ||  |  ||    _]
                |  |  ||     ||    \\  |  | |   |   ||  _  |    /   \\_ |  _  ||  |    /  \\ |  |  |  |     ||  |  ||   [_\s
                |  |  ||     ||  .  \\ |  | |   |   ||  |  |    \\     ||  |  ||  |    \\    |  |  |  |     ||  |  ||     |
                |__|__| \\___/ |__|\\_||____||___|___||__|__|     \\____||__|__||__|     \\___|  |__|   \\___/ |__|__||_____|
                """;
    }


}
