package boundary;

import controller.LoginController;
import entity.user.Applicant;
import entity.user.Manager;
import entity.user.Officer;
import entity.user.User;
import java.util.Scanner;

public class Menu {
    private final LoginController loginController;

    public Menu() {
        this.loginController = new LoginController();
    }

    public void displayMenu() {
        try (Scanner sc = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                int choice;
                System.out.print("""
                    --------------------
                         Login Menu
                    --------------------
                    1. Login
                    2. Register
                    3. Change password
                    4. Exit
                    """);
                System.out.print("Option: ");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> {
                        login(sc);
                    }
                    case 2 -> {
                        System.out.println("change password");
                    }
                    case 3 -> {

                    }
                    case 4 -> {
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

    private void login(Scanner sc) {
        System.out.print("Enter NRIC: ");
        String nric = sc.nextLine().trim().toUpperCase();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User user = LoginController.validateLogin(nric, password);
        if (user != null) {
            System.out.println("Login successful as " + user.getUserRole());
            switch (user.getUserRole()) {
                case APPLICANT -> {
                    ApplicantMain applicantMain = new ApplicantMain((Applicant) user, sc);
                }
                case OFFICER -> {
                    OfficerMain officerMain = new OfficerMain((Officer) user, sc);
                }
                case MANAGER -> {
                    ManagerMain managerMain = new ManagerMain((Manager) user, sc);
                }
            }
        } else {
            System.out.println("Invalid credentials, please try again!");
        }
    }
}
