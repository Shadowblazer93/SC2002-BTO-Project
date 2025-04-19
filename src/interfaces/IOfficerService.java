package interfaces;

import entity.project.BTOProject;
import entity.user.Officer;
import java.util.Map;

public interface IOfficerService {
    
    Officer createOfficer(String nric, String name, String password, int age, String maritalStatus);
    
    Officer getOfficer(String nric);
    
    Map<String, Officer> getAllOfficers();
    
    String registerProject(Officer officer, BTOProject project);
}
