package interfaces;

import entity.user.Manager;
import java.util.Map;

public interface IManagerService {

    Manager createManager(String nric, String name, String password, int age, String maritalStatus);
    
    Manager getManager(String nric);
    
    Map<String, Manager> getAllManagers();
    
}