package controller.user;

import entity.user.Manager;
import java.util.HashMap;
import java.util.Map;

public class ManagerController {
    private static final Map<String, Manager> allManagers = new HashMap<>(); // Nric + Manager

    public static Manager createManager(String nric, String name, String password, int age, String maritalStatus) {
        Manager manager = new Manager(nric, name, password, age, maritalStatus);
        allManagers.put(nric, manager);
        return manager;
    }

    public static Manager getManager(String nric) {
        return allManagers.get(nric);
    }

    public static Map<String, Manager> getAllManagers() {
        return allManagers;
    }
}
