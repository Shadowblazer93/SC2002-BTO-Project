package boundary;

import controller.LoginController;
import entity.user.Applicant;
import entity.user.Manager;
import entity.user.Officer;
import entity.user.User;
import enums.*;
import java.util.Scanner;

public class Menu {
    ApplicantMain applicantMain = new ApplicantMain();
    OfficerMain officerMain = new OfficerMain();
    ManagerMain managerMain = new ManagerMain();

    public void displayMenu() {
        try (Scanner sc = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                int choice;
                System.out.print(defColor.PURPLE+"""
                    --------------------
                         Login Menu
                    -------------------- 
                    """+defColor.BLUE+ 
                    """
                    1. Login
                    2. Change password
                    3. Exit
                    """+defColor.RESET);
                while (true) {
                    System.out.print("Option (1-3): ");
                    if (sc.hasNextInt()) {
                        choice = sc.nextInt();
                        sc.nextLine(); // consume newline
                        if (choice >= 1 && choice <= 3) {
                            break; // valid option, exit loop
                        } else {
                            System.out.println("Invalid option. Please enter a number between 1 and 3.");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a number.");
                        sc.nextLine(); // consume invalid token
                    }
                }
                    
                switch (choice) {
                    case 1 -> {
                        login(sc);
                    }
                    case 2 -> {
                        changePassword(sc);
                    }
                    case 3 -> {
                        System.out.print("Bye!");
                        // End program
                        running = false;
                    }
                    default -> {
                        System.out.println("Invalid option");
                    }
                }
            }
        }
    }

    private User loginInput(Scanner sc) {
        boolean valid = false;
        String nric = null;
        while (!valid) {
            System.out.print("Enter NRIC: ");
            nric = sc.nextLine().trim().toUpperCase();
            if (LoginController.checkNRIC(nric)) {
                valid = true;
                break;
            }
            System.out.println("Invalid NRIC format. Please try again.");
        }
        
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User user = LoginController.validateLogin(nric, password);
        return user;
    }

    private void login(Scanner sc) {
        User user = loginInput(sc);
        if (user != null) {
            System.out.println("Login successful as " + user.getUserRole());
            switch (user.getUserRole()) {
                case APPLICANT -> {
                    applicantMain.displayMenu((Applicant) user, sc);
                }
                case OFFICER -> {
                    officerMain.displayMenu((Officer) user, sc);
                }
                case MANAGER -> {
                    managerMain.displayMenu((Manager) user, sc);
                }
            }
        } else {
            System.out.println("Invalid credentials, please try again!");
        }
    }

    private void changePassword(Scanner sc) {
        User user = loginInput(sc);
        if (user == null) {
            System.out.println("Invalid credentials, please try again!");
            return;
        }
        System.out.print("Enter new password: ");
        String newPassword = sc.next();
        user.setPassword(newPassword);
        System.out.println("Password successfully changed.");
    }
}
