package controller.user;

import entity.project.BTOProject;
import entity.user.Officer;
import interfaces.IOfficerService;
import java.util.HashMap;
import java.util.Map;

public class OfficerController implements IOfficerService {
    
    private static final Map<String, Officer> allOfficers = new HashMap<>(); // NRIC + Officer

    @Override
    public Officer createOfficer(String nric, String name, String password, int age, String maritalStatus) {
        Officer officer = new Officer(nric, name, password, age, maritalStatus);
        allOfficers.put(nric, officer);
        return officer;
    }

    @Override
    public Officer getOfficer(String nric) {
        return allOfficers.get(nric);
    }

    @Override
    public Map<String, Officer> getAllOfficers() {
        return allOfficers;
    }

    @Override
    public String registerProject(Officer officer, BTOProject project) {
        // Rule 0: Cannot register if already assigned to a project
        if (officer.getAssignedProject() != null) {
            return "You are already assigned to project: " + officer.getAssignedProject().getProjectName() + 
                   ". Cannot register for another project.";
        }

        String projectName = project.getProjectName();
        // Rule 1: Cannot be applicant for project
        if (officer.getApplication() != null &&
            officer.getApplication().getProjectName() != null &&
            officer.getApplication().getProjectName().equals(project.getProjectName())) {
            return "You are already an applicant for this project. Cannot register as officer.";
        }

        // Rule 2: Cannot be registered for same project
        if (officer.getRegisteredProjects() != null &&
            officer.getRegisteredProjects().get(projectName) != null) {
            return "You have already registered for this project. Cannot register as officer.";
        }
    
        // Rule 3: Cannot be registered as officer for overlapping project
        for (BTOProject registered : officer.getRegisteredProjects().values()) {
            boolean overlaps = !(project.getClosingDate().isBefore(registered.getOpeningDate()) ||
                                 project.getOpeningDate().isAfter(registered.getClosingDate()));
            if (overlaps) {
                return "You are already registered as officer for another project in the same period.";
            }
        }
    
        return "success"; // Passed all checks
    }
    
}
