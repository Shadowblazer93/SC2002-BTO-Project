package boundary;

import entity.user.Manager;
import enums.defColor;
import interfaces.IApplicationService;
import interfaces.IEnquiryService;
import interfaces.IProjectService;
import interfaces.IRegistrationService;
import java.util.Scanner;

/**
 * Boundary class that handles user interface for HDB Manaagers.
 * This class allows managers to navigate to different menus for management of
 * BTO Projects, enquiries, applications and officer registrations
 */
public class ManagerMain {

    BTOProjectMain btoProjectMain;
    RegistrationMain registrationMain;
    EnquiryMain enquiryMain;
    ApplicationMain applicationMain;

    private final IApplicationService applicationService;
    private final IEnquiryService enquiryService;
    private final IProjectService projectService;
    private final IRegistrationService registrationService;

    public ManagerMain(IApplicationService applicationService, IEnquiryService enquiryService, 
                        IProjectService projectService, IRegistrationService registrationService) {
        this.applicationService = applicationService;
        this.enquiryService = enquiryService;
        this.projectService = projectService;
        this.registrationService = registrationService;

        this.applicationMain = new ApplicationMain(applicationService, projectService);
        this.enquiryMain = new EnquiryMain(enquiryService);
        this.btoProjectMain = new BTOProjectMain(projectService);
        this.registrationMain = new RegistrationMain(registrationService);
        
    }

    /**
     * Displays the main menu for a logged-in manager and handles navigation to various functionalities
     * @param manager Manager currently logged-in
     * @param sc Scanner object for user input
     */
    public void displayMenu(Manager manager, Scanner sc) {
        boolean running = true;
        while (running) {
            System.out.printf(defColor.PURPLE + """

                Hi %s
                =========================
                  HDB Manager Main Page
                -------------------------
                """ + defColor.BLUE + """
                1. Manage projects
                2. Manage enquiries
                3. Manage applications
                4. Manage registrations
                5. Logout
                """ + defColor.PURPLE + 
                "=========================\n" + defColor.RESET, manager.getName());
            System.out.print("Option: ");
            
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                sc.nextLine(); // clear invalid input
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine(); // clear newline

            switch (choice) {
                case 1 -> btoProjectMain.displayMenu(manager, sc);
                case 2 -> enquiryMain.displayMenuManager(sc, manager);
                case 3 -> applicationMain.displayMenuManager(sc, manager);
                case 4 -> registrationMain.displayMenu(manager, sc);
                case 5 -> {
                    System.out.println("Logging out...");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please select between 1 and 5.");
            }
        }
    } 
}
