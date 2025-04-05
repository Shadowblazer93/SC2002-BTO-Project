package boundary;

import entity.user.Manager;
import enums.UserRole;
import java.util.Scanner;

public class ManagerMain {
    BTOProjectMain btoProjectMain = new BTOProjectMain();
    
    public static void main(String[] args) {
        Manager manager = new Manager("123", "password", UserRole.MANAGER);
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
                    4. Logout
                    """, manager.getUserID());
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
                default -> {
                }
            }
        }
    } 
}
