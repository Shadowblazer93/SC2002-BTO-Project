package boundary;

import controller.LoginController;
import entity.user.Applicant;
import entity.user.Manager;
import entity.user.Officer;
import entity.user.User;
import enums.*;
import interfaces.IApplicationService;
import interfaces.IEnquiryService;
import interfaces.IProjectService;
import interfaces.IRegistrationService;
import java.util.Scanner;

/**
 * Main menu interface class for user interaction with the system
 * Allows users to login, change their password or exit the program
 * Upon login, users are redirecated to their respective role-specific menu
 */
public class Menu {

    private final ApplicantMain applicantMain;
    private final OfficerMain officerMain;
    private final ManagerMain managerMain;

    private final IApplicationService applicationService;
    private final IEnquiryService enquiryService;
    private final IProjectService projectService;
    private final IRegistrationService registrationService;

    public Menu(IApplicationService applicationService, IEnquiryService enquiryService, 
                IProjectService projectService, IRegistrationService registrationService) {
        this.applicationService = applicationService;
        this.enquiryService = enquiryService;
        this.projectService = projectService;
        this.registrationService = registrationService;

        this.applicantMain = new ApplicantMain(applicationService, enquiryService, projectService);
        this.officerMain = new OfficerMain(applicationService, enquiryService, projectService, registrationService);
        this.managerMain = new ManagerMain(applicationService, enquiryService, projectService, registrationService);
    }

    /**
     * Display main meny to user and handles user input
     */
    public void displayMenu() {
        try (Scanner sc = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                int choice;
                System.out.print(defColor.PURPLE+"""
                    ====================
                         Login Menu
                    -------------------- 
                    """+defColor.BLUE+ 
                    """
                    1. Login
                    2. Change password
                    3. Exit
                    """ + defColor.PURPLE +
                    "====================\n" + defColor.RESET);
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

    /**
     * Prompt user to input NRIC and password and validates their credentials
     * @param sc Scanner object for user input
     * @return Authenticated {@link User} object if credentials are valid, {@code null} otherwise
     */
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

    /**
     * Handle login flow for users and redirects authenticated users
     * @param sc Scanner object for user input
     */
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

    /**
     * Allow user to change their password after verifying their credentials
     * Validates new password for strength requirements
     * @param sc Scanner object for user input
     */
    private void changePassword(Scanner sc) {
        User user = loginInput(sc);
        if (user == null) {
            System.out.println("Invalid credentials, please try again!");
            return;
        }
        System.out.println("""
                New password must be:
                 - At least 8 characters
                 - Contain at least 1 lowercase and 1 uppercase letter
                 - Contain at least 1 digit
                 - Contain at least 1 special character""");
        String newPassword;
        while (true) {
            System.out.print("Enter new password: ");
            newPassword = sc.nextLine();
            String msg = LoginController.strongPassword(newPassword);
            if (msg.equals("success")) {
                break;
            } else {
                System.out.println(msg);
            }
        }
        user.setPassword(newPassword);
        System.out.println("Password successfully changed.");
    }
}
