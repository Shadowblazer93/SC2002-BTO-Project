package boundary;

import entity.user.Manager;
import java.util.Scanner;

public class ManagerMain {
    BTOProjectMain btoProjectMain = new BTOProjectMain();

    public ManagerMain(Manager manager) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.printf("""
                    Hi %s
                    HDB Manager Main Page
                    ---------------------
                    1. Manage projects
                    2. Manage enquiries
                    3. Manage applications
                    """, manager.getUserID());
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
