package controller;

import entity.project.BTOProject;
import entity.registration.Registration;
import entity.user.*;
import enums.RegistrationStatus;
import interfaces.IRegistrationService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller class for managing officer registrations to BTO Projects
 * This class handles the creation, deletion and management of Registrations
 */
public class RegistrationController implements IRegistrationService {
    
    /**
     * Store all registrations grouped by project name
     */
    private static Map<String, List<Registration>> allRegistrations = new HashMap<>();   // Project and List of registrations
    
    /**
     * Track the next registration ID to be assigned
     */
    private static int registrationCount = 1;

    /**
     * Get all project registrations in sysatem
     * @return Map of project names to their list of Registrations
     */
    @Override
    public Map<String, List<Registration>> getAllRegistrations() {
        return allRegistrations;
    }

    /**
     * Get registrations for a specific project
     * @param projectName Name of project
     * @return List of Registration for the project
     */
    @Override
    public List<Registration> getRegistrationsByProject(String projectName) {
        return allRegistrations.get(projectName);
    }

    /**
     * Add registration to list associated with a project
     * @param project Name of the project
     * @param registration Registration to add
     */
    @Override
    public void addRegistration(String project, Registration registration) {
        if (allRegistrations.containsKey(project)) {
            allRegistrations.get(project).add(registration);
        } else {
            List<Registration> registrations = new ArrayList<>();
            registrations.add(registration);
            allRegistrations.put(project, registrations);
        }
    }

    /**
     * Creates a new registration and adds it to the system
     * @param id Registration ID (0 to auto-generate)
     * @param officer Officer registering for project
     * @param projectName Name of BTO Project
     * @param registrationDate Date of registration
     * @param status Status of registration
     * @return Newly created Registration object
     */
    @Override
    public Registration createRegistration(int id, Officer officer, String projectName, LocalDate registrationDate, RegistrationStatus status) {
        registrationCount = Math.max(registrationCount,id);
        Registration registration = new Registration(registrationCount, officer, projectName, registrationDate, status);
        registrationCount++;
        addRegistration(projectName, registration);
        return registration;
    }

    /**
     * Register officer to a project. Creates a new registration with PENDING status
     * @param officer Officer to be registered
     * @param project Project to register for
     * @return Created {@link Registration} object
     */
    @Override
    public Registration registerProject(Officer officer, BTOProject project)  {
        Registration registration = createRegistration(0, officer, project.getProjectName(), LocalDate.now(), RegistrationStatus.PENDING);
        officer.addRegisteredProject(project);  // Add project to officer's registered projects
        project.addRegistration(registration);  // Add registration to project
        return registration;
    }

    /**
     * Approve registration if it is valid and there are officer slots available in project
     * @param manager Manager approving registration
     * @param registration Registration to approve
     * @return Message indicating result of the operation
     */
    @Override
    public String approveRegistration(Manager manager, Registration registration) {
        if (!checkStatus(registration)) {
            return "Registration has already been processed";
        }
        Map<String, BTOProject> managedProjects = manager.getManagedProjects();
        BTOProject project = managedProjects.get(registration.getProjectName());
        if (project == null) {
            return "You are not managing this project.";
        }
        if (project.getAssignedOfficers().size() >= project.getAvailableOfficerSlots()) {
            return "No officer slots left for this project.";
        }

        Officer officer = registration.getOfficer();

        officer.assignProject(project);
        registration.approveRegistration(); // Set as approved
        project.assignOfficer(registration);   // Add to project
        return "Success";
    }

    /**
     * Reject a registration if it is still pending
     * @param manager Manager rejecting registration
     * @param registration Registration to be rejected
     * @return Message indicating result of the operation
     */
    @Override
    public String rejectRegistration(Manager manager, Registration registration) {
        if (!checkStatus(registration)) {
            return "Registration has already been processed";
        }
        Map<String, BTOProject> managedProjects = manager.getManagedProjects();
        BTOProject project = managedProjects.get(registration.getProjectName());
        if (project == null) {
            return "You are not managing this project.";
        }

        registration.rejectRegistration();          // Set as rejected
        return "Success";
    }

    /**
     * Helper class to check if a registration is still pending and can be processed
     * @param registration Registration to check
     * @return true if registration is pending, false otherwise
     */
    private boolean checkStatus(Registration registration) {
        switch (registration.getStatus()) {
            case APPROVED, REJECTED -> {
                return false;
            }
            default -> {
                return true;
            }
        }
    }
}
