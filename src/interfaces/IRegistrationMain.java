package interfaces;

import entity.user.Manager;
import java.util.Scanner;


/**
 * Interface for the main boundary logic of registration-related features.
 * Classes implementing this interface will provide a user interface for 
 * managing registration options, such as assigning applicants or officers.
 */
public interface IRegistrationMain {

    /**
     * Displays the main registration menu for the Manager user.
     * The implementation is responsible for handling the menu logic
     * and interactions based on user input.
     *
     * @param manager The currently logged-in manager
     * @param sc      A {@code Scanner} instance for reading user input
     */
    void displayMenu(Manager manager, Scanner sc);
    
}
