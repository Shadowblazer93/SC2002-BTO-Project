package boundary;

import controller.RegistrationController;
import entity.registration.Registration;
import entity.user.Manager;
import java.util.*;


public class RegistrationMain {
    PrintRegistrations printRegistrations = new PrintRegistrations();
    RegistrationController registrationController = new RegistrationController();

    public void displayMenu(Manager manager) {
        try (Scanner sc = new Scanner(System.in)) {
            int choice = 0;
            while (choice != 4) {
                System.out.println("""
                    --------------------------
                      Registration Main Page
                    --------------------------
                    1. View all registrations
                    2. Approve registrations
                    3. Reject registrations
                    4. Exit
                    """);
                System.out.print("Option: ");
                choice = sc.nextInt();

                switch(choice) {
                    case 1 -> {
                        // Print registrations for each project managed
                        printRegistrations.printMapList(registrationController.getAllRegistrations());
                    }
                    case 2 -> {
                        // Print projects managed
                        System.out.println("Select project: ");
                        
                        List<Registration> registrationList = null;

                        // Print registrations in the project
                        printRegistrations.printList(registrationList);

                        System.out.println("Select registrations to approve: ");
                    }
                    case 3 -> {
                        // Print projects managed
                        System.out.println("Select project: ");

                        // Print registrations in the project

                        System.out.println("Select registrations to reject: ");
                    }
                    default -> {
                        System.out.println("Invalid option.");
                    }
                }
            }
        }
    }
}
