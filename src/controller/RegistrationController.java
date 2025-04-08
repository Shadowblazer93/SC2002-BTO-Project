package controller;

import entity.project.BTOProject;
import entity.registration.Registration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class RegistrationController {
    private static Map<String, List<Registration>> allRegistrations;   // Project and List of registrations

    public RegistrationController() {
        allRegistrations = new HashMap<>();
    }

    // Add registration to list of registrations for a project
    public static void addRegistration(String project, Registration registration) {
        if (allRegistrations.containsKey(project)) {
            allRegistrations.get(project).add(registration);
        } else {
            List<Registration> registrations = new ArrayList<>();
            registrations.add(registration);
            allRegistrations.put(project, registrations);
        }
    }

    public Map<String, List<Registration>> getAllRegistrations() {
        return allRegistrations;
    }

    public boolean approveRegistration(BTOProject project, Registration registration) {
        if (project.getPendingRegistrations().isEmpty()) {
            System.out.println("No registrations found for this project.");
            return false;
        } else if (!project.getPendingRegistrations().contains(registration)) {
            System.out.println("This registration does not exist for this project.");
            return false;
        }

        registration.approveRegistration(); // Set as approved
        project.addOfficer(registration);   // Add to project
        return true;
    }

    public boolean rejectRegistration(BTOProject project, Registration registration) {
        if (project.getPendingRegistrations().isEmpty()) {
            System.out.println("No registrations found for this project.");
            return false;
        } else if (!project.getPendingRegistrations().contains(registration)) {
            System.out.println("This registration does not exist for this project.");
            return false;
        }

        registration.rejectRegistration();          // Set as rejected
        project.removeRegistration(registration);   // Remove from pending registrations in project
        return true;
    }
}
