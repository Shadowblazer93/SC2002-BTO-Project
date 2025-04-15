package controller;

import entity.project.BTOProject;
import entity.registration.Registration;
import entity.user.Officer;
import enums.RegistrationStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationController {
    // NRIC + Registration
    private static Map<String, List<Registration>> allRegistrations = new HashMap<>();   // Project and List of registrations
    private static int registrationCount = 1;   // Track registration ID

    public static int getRegistrationCount (){
        return registrationCount;
    }

    public static Registration createRegistration(int id, Officer officer, String projectName, LocalDate registrationDate, RegistrationStatus status) {
        registrationCount = Math.max(registrationCount,id);
        Registration registration = new Registration(registrationCount, officer, projectName, registrationDate, status);
        registrationCount++;
        addRegistration(projectName, registration);
        return registration;
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

    public static Map<String, List<Registration>> getAllRegistrations() {
        return allRegistrations;
    }

    public static List<Registration> getRegistrationsByProject(String projectName) {
        return allRegistrations.get(projectName);
    }

    public static Registration registerProject(Officer officer, BTOProject project)  {
        Registration registration = createRegistration(0, officer, project.getProjectName(), LocalDate.now(), RegistrationStatus.PENDING);
        officer.registerProject(project);       // Add project to officer's registered projects
        project.addRegistration(registration);  // Add registration to project
        return registration;
    }

    public static String approveRegistration(BTOProject project, Registration registration) {
        if (project.getAssignedOfficers().size() >= project.getAvailableOfficerSlots()) {
            return "No officer slots left for this project.";
        }

        Officer officer = registration.getOfficer();
        if (project.getRegistrations().isEmpty()) {
            return "No registrations found for this project.";
        } else if (!project.getRegistrations().containsKey(officer.getNRIC())) {
            return "This registration does not exist for this project.";
        }

        officer.assignProject(project);
        registration.approveRegistration(); // Set as approved
        project.assignOfficer(registration);   // Add to project
        return "Success";
    }

    public static String rejectRegistration(BTOProject project, Registration registration) {
        Officer officer = registration.getOfficer();
        if (project.getRegistrations().isEmpty()) {
            return "No registrations found for this project.";
        } else if (!project.getRegistrations().containsKey(officer.getNRIC())) {
            return "This registration does not exist for this project.";
        }

        registration.rejectRegistration();          // Set as rejected
        return "Success";
    }
}
