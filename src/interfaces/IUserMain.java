package interfaces;

import java.util.Scanner;

/**
 * Generic interface for user-facing main menu interactions.
 * This allows multiple user types (e.g., Applicant, Officer, Manager) to implement
 * customized menu displays based on their roles.
 *
 * @param <T> The user type implementing the interface (e.g., Applicant, Officer).
 */
public interface IUserMain<T> {

    /**
     * Displays the main menu and handles interaction logic for the specified user.
     *
     * @param user The logged-in user instance of type {@code T}.
     * @param sc   A {@code Scanner} for reading user input.
     */
    void displayMenu(T user, Scanner sc);
    
}
