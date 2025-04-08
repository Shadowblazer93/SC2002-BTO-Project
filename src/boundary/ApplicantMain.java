package boundary;

import java.util.Scanner;

import entity.user.Applicant;

public class ApplicantMain {
    public ApplicantMain(Applicant applicant) {
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        while (choice!=7) {
            System.out.printf("""
                    Hi %s
                    ------------------------------
                          Applicant Main Page
                    ------------------------------
                    1. View project list
                    2. Apply to project
                    3. Withdraw from Project
                    4. Submit Enquiry
                    5. Edit Enquiry
                    6. Delete Enquiry
                    7. Logout
                    ------------------------------
                    """, applicant.getName());
            
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    // read from csv and filter for flatType
                    break;
            
                case 2:
                    break;

                case 3:
                    break;

                case 4:
                    break;
                
                case 5:
                    break;
                
                case 6:
                    break;
                
                case 7:
                    return;

                default:
                    System.out.println("Unknown choice!");
                    break;
            }
        }
    }
}
