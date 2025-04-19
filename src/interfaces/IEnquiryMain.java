package interfaces;

import entity.user.Officer;
import java.util.Scanner;

public interface IEnquiryMain {
    
    void viewProjectEnquiries(Officer officer);
    
    void displayMenuOfficer(Scanner sc, Officer officer);

}
