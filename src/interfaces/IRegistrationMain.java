package interfaces;

import entity.user.Manager;
import java.util.Scanner;

/**
 * Interface for Registration boundary class.
 * Defines the core functionality required for the registration management user interface.
 */
public interface IRegistrationMain {
    /**
     * Displays the registration management menu for managers.
     * 
     * @param manager The manager using the system
     * @param sc Scanner for reading user input
     */
    void displayMenu(Manager manager, Scanner sc);
    
}
