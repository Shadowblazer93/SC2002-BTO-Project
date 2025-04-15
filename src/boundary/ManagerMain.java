package boundary;

import entity.user.Manager;
import java.util.Scanner;

public class ManagerMain {
    BTOProjectMain btoProjectMain = new BTOProjectMain();
    RegistrationMain registrationMain = new RegistrationMain();
    EnquiryMain enquiryMain = new EnquiryMain();
    ApplicationMain applicationMain = new ApplicationMain();

    public ManagerMain(Manager manager, Scanner sc) {
        boolean running = true;
        while (running) {
            System.out.printf("""
                
                Hi %s
                -------------------------
                  HDB Manager Main Page
                -------------------------
                1. Manage projects
                2. Manage enquiries
                3. Manage applications
                4. Manage registrations
                5. Logout
                """, manager.getName());
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
