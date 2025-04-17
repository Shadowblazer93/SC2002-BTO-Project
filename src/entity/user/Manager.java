package entity.user;

import entity.project.BTOProject;
import enums.UserRole;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a manager in the BTOMS system
 * A manager can only handle 1 project within an application period
 */
public class Manager extends User {
	
	/**
	 * Map of all projects managed by the manager
	 * Key: Project name, Value: BTOProject object
	 */
	private Map<String, BTOProject> managedProjects;

	/**
	 * The current project handled by the manager
	 */
	private BTOProject currentProject;

	/**
	 * Constructor for Manager class
	 * @param nric Manager's NRIC
	 * @param name Manager's name
	 * @param password Manager's password
	 * @param age Manager's age
	 * @param maritalStatus Manager's marital status
	 */
	public Manager(String nric, String name, String password, int age, String maritalStatus) {
		super(nric, name, password, age, maritalStatus, UserRole.MANAGER);
		managedProjects = new HashMap<>();
		currentProject = null;
	}

	/**
	 * Get the map of managed projects
	 * @return A map of manager's projects
	 */
	public Map<String, BTOProject> getManagedProjects() {
		return managedProjects;
	}

	/**
	 * Add project to manager's list of projects
	 * @param project BTOProject to be added
	 */
	public void addProject(BTOProject project) {
		managedProjects.put(project.getProjectName(), project);
	}

	/**
	 * Remove project from manager's list of projects
	 * @param project BTOProject to be removed
	 */
	public void deleteProject(BTOProject project) {
		managedProjects.remove(project.getProjectName());
	}

	/**
	 * Set the current project handled by manager
	 * @param project BTOProject to be set as current project
	 */
	public void setCurrentProject(BTOProject project) {
		this.currentProject = project;
	}

	/**
	 * Get the current project handled by manager
	 * @return Current BTOProject handled by manager
	 */
	public BTOProject getCurrentProject() {
		return currentProject;
	}
}
