package boundary;

import entity.user.Officer;
import java.util.*;
public class OfficerMain {

    
    public static void main(String[] args) {
        public OfficerMain(Officer officer){
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.printf("""
                    Hi %s
                    ------------------------------
                        HDB Officer Main Page
                    ------------------------------
                    1. View and manage flat bookings
                    2. View and reply to enquiries
                    3. Update applicant status
                    4. Generate receipt for bookings
                    5. Logout
                    ------------------------------
                    """, officer.getUserID());
            
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
        }
    }

}
