package entity.user;

import entity.project.BTOProject;
import enums.UserRole;
import java.util.HashMap;
import java.util.Map;

public class Manager extends User {
	// List of projects owned by manager
	private Map<String, BTOProject> managedProjects;
	private BTOProject currentProject;	// Current project handled by manager

	public Manager(String nric, String name, String password, int age, String maritalStatus) {
		super(nric, name, password, age, maritalStatus, UserRole.MANAGER);
		managedProjects = new HashMap<>();
		currentProject = null;
	}

	public Map<String, BTOProject> getManagedProjects() {
		return managedProjects;
	}

	public void addProject(BTOProject project) {
		managedProjects.put(project.getProjectName(), project);
	}

	public void deleteProject(BTOProject project) {
		managedProjects.remove(project.getProjectName());
	}

	public void setCurrentProject(BTOProject project) {
		this.currentProject = project;
	}

	public BTOProject getCurrentProject() {
		return currentProject;
	}
}
