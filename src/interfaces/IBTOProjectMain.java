package interfaces;

import entity.user.Manager;
import java.util.Scanner;

/**
 * IBTOProjectMain interface defines operations for managing the BTO project menu in the system.
 * Provides a method to display the menu for managers.
 */
public interface IBTOProjectMain {

    /**
     * Displays the BTO project menu for managers and handles user input.
     *
     * @param manager Manager interacting with the system.
     * @param sc      Scanner for reading user input.
     */
    void displayMenu(Manager manager, Scanner sc);
}
