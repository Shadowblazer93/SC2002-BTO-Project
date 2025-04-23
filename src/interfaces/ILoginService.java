package interfaces;

import entity.user.User;

/**
 * Interface for login-related services.
 */
public interface ILoginService {
    
    /**
     * Validates login credentials against stored user information.
     *
     * @param nric NRIC of the user
     * @param password Password of the user
     * @return User object if credentials are valid, null otherwise
     */
    User validateLogin(String nric, String password);
    
    /**
     * Checks if a given NRIC exists in the system.
     *
     * @param nric NRIC to check
     * @return true if NRIC exists, false otherwise
     */
    boolean checkNRIC(String nric);
    
    /**
     * Evaluates the strength of a password.
     *
     * @param password Password to check
     * @return A message indicating whether the password is strong or suggesting improvements
     */
    String strongPassword(String password);
    
}
