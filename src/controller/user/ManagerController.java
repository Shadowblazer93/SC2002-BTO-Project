package controller.user;

import entity.user.Manager;
import interfaces.IManagerService;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller class responsible for managing Manager Entities
 */
public class ManagerController implements IManagerService {

    /**
     * Store all managers in the system, keyed by their NRIC
     */
    private static final Map<String, Manager> allManagers = new HashMap<>();

    /**
     * Creates new Manager object and adds it to the system
     * @param nric Manager's NRIC
     * @param name Manager's name
     * @param password Manager's password
     * @param age Manager's age
     * @param maritalStatus Manager's marital status
     * @return Newly created Manager object
     */
    @Override
    public Manager createManager(String nric, String name, String password, int age, String maritalStatus) {
        Manager manager = new Manager(nric, name, password, age, maritalStatus);
        allManagers.put(nric, manager);
        return manager;
    }

    /**
     * Retrieve manager by their NRIC
     * @param nric NRIC of manager to retrieve
     * @return Manager object if found, null otherwise
     */
    @Override
    public Manager getManager(String nric) {
        return allManagers.get(nric);
    }

    /**
     * Retrieve a map of all managers in the system
     * @return Map where key is the Manager's NRIC and value is the corresponding Manager object
     */
    @Override
    public Map<String, Manager> getAllManagers() {
        return allManagers;
    }
    
}
