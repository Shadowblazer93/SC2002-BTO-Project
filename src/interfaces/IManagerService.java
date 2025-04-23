package interfaces;

import entity.user.Manager;
import java.util.Map;

/**
 * IManagerService interface defines operations for managing managers in the system.
 * Provides methods to create, retrieve, and manage manager data.
 */
public interface IManagerService {

    /**
     * Creates a new manager and adds them to the system.
     *
     * @param nric          NRIC of the manager.
     * @param name          Name of the manager.
     * @param password      Password for the manager's account.
     * @param age           Age of the manager.
     * @param maritalStatus Marital status of the manager (e.g., "single", "married").
     * @return Newly created Manager object.
     */
    Manager createManager(String nric, String name, String password, int age, String maritalStatus);

    /**
     * Retrieves a manager by their NRIC.
     *
     * @param nric NRIC of the manager to retrieve.
     * @return Manager object associated with the given NRIC, or null if not found.
     */
    Manager getManager(String nric);

    /**
     * Retrieves all managers in the system.
     *
     * @return Map of all managers, keyed by their NRIC.
     */
    Map<String, Manager> getAllManagers();
}