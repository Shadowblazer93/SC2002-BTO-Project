package interfaces;

import entity.user.Manager;
import entity.user.Officer;
import java.util.Scanner;

/**
 * Defines main application menu interface for different user roles (Officer and Manager)
 */
public interface IApplicationMain {

    /**
     * Display main menu options for an officer
     * @param sc Scanner object for user input
     * @param officer Officer currently logged in
     */
    void displayMenuOfficer(Scanner sc, Officer officer);

    /**
     * Display main menu options for a manager
     * @param sc Scanner object for user input
     * @param manager Manager currently logged in
     */
    void displayMenuManager(Scanner sc, Manager manager);
    
}
