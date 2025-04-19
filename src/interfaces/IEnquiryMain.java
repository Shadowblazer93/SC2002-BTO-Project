package interfaces;

import entity.user.Manager;
import entity.user.Officer;
import java.util.Scanner;

public interface IEnquiryMain {
    
    void displayMenuOfficer(Scanner sc, Officer officer);

    void displayMenuManager(Scanner sc, Manager manager);

}
