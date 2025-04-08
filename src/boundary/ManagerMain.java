package boundary;

import entity.user.Manager;
import enums.UserRole;
import java.util.Scanner;

public class ManagerMain {
    BTOProjectMain btoProjectMain = new BTOProjectMain();
    RegistrationMain registrationMain = new RegistrationMain();
    
    public static void main(String[] args) {
        Manager manager = new Manager("123", "John", "password", UserRole.MANAGER, 1, "");
        ManagerMain managerMain = new ManagerMain(manager);
    }

    public ManagerMain(Manager manager) {
        try (Scanner sc = new Scanner(System.in)) {
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
            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> {
                    btoProjectMain.displayMenu(manager);
                }
                case 2 -> {

                }
                case 3 -> {

                }
                case 4 -> {
                    registrationMain.displayMenu(manager);
                }
                default -> {
                }
            }
        }
    } 
}
