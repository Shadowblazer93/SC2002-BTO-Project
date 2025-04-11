package controller.user;

import entity.user.Manager;
import entity.user.Officer;
import java.util.HashMap;
import java.util.Map;

public class OfficerController {
    private static final Map<String, Officer> allOfficers = new HashMap<>(); // NRIC + Officer

    public Officer createOfficer(String nric, String name, String password, int age, String maritalStatus) {
        Officer officer = new Officer(nric, name, password, age, maritalStatus);
        allOfficers.put(nric, officer);
        return officer;
    }

    public Officer getOfficer(String nric) {
        return allOfficers.get(nric);
    }

    public Map<String, Officer> getAllOfficers() {
        return allOfficers;
    }

    // Check if officer is eligible for registration of project
    public String canRegisterProject(Officer officer) {   
        String message = "success";
        // Already assigned to project
        if (officer.getAssignedProject() != null) {
            message = "You are already assigned to a project: " + officer.getAssignedProject().getProjectName();
            return message;
        } else if (false) {
            // Check if have applied for any project as applicant
        }
        return message;
    }
}
