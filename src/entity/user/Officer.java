package entity.user;

import entity.project.BTOProject;
import enums.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an HDB Officer, which is a specialized type of Applicant.
 * Officers can register for and be assigned to BTO projects.
 */
public class Officer extends Applicant {
    private BTOProject assignedProject;
    private Map<String, BTOProject> registeredProjects;

    /**
     * Constructs an Officer object with the given attributes.
     *
     * @param nric NRIC of the officer
     * @param name Name of the officer
     * @param password Password for login
     * @param age Age of the officer
     * @param maritalStatus Marital status of the officer
     */
    public Officer(String nric, String name, String password, int age, String maritalStatus) {
        super(nric, name, age, maritalStatus, password);    // Applicant constructor
        this.setUserRole(UserRole.OFFICER);
        assignedProject = null;
        registeredProjects = new HashMap<>();
    }

    /**
     * Gets the BTO project currently assigned to the officer.
     *
     * @return The assigned BTOProject, or null if none
     */
    public BTOProject getAssignedProject() {
        return assignedProject;
    }

    /**
     * Assigns a BTO project to the officer.
     *
     * @param project The BTOProject to assign
     */
    public void assignProject(BTOProject project) {
        this.assignedProject = project;
    }

    /**
     * Registers the officer for a BTO project.
     *
     * @param project The BTOProject to register for
     */
    public void addRegisteredProject(BTOProject project) {
        registeredProjects.put(project.getProjectName(), project);
    }

    /**
     * Gets all the BTO projects this officer has registered for.
     *
     * @return A map of project names to BTOProject objects
     */
    public Map<String, BTOProject> getRegisteredProjects() {
        return registeredProjects;
    }

    /**
     * Checks whether the officer has already registered for a specific project.
     *
     * @param projectName The name of the project
     * @return true if already registered, false otherwise
     */
    public boolean isAlreadyRegistered(String projectName) {
        return registeredProjects.containsKey(projectName);
    }
}