package interfaces;

import entity.user.Manager;
import entity.user.Officer;
import java.util.Scanner;

/**
 * IEnquiryMain interface defines the operations for managing enquiry menus in the BTO system.
 * Provides methods to display enquiry menus for officers and managers.
 */
public interface IEnquiryMain {
    /**
     * Displays enquiry menu for officers and handles user input.
     *
     * @param sc      Scanner for reading user input.
     * @param officer Officer interacting with the system.
     */
    void displayMenuOfficer(Scanner sc, Officer officer);

    /**
     * Displays enquiry menu for managers and handles user input.
     *
     * @param sc      Scanner for reading user input.
     * @param manager Manager interacting with the system.
     */
    void displayMenuManager(Scanner sc, Manager manager);

}
