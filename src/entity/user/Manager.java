package entity.user;

import entity.project.BTOProject;
import enums.UserRole;
import java.util.HashMap;
import java.util.Map;

public class Manager extends User {
	// List of projects owned by manager
	private static Map<String, BTOProject> managedProjects= new HashMap<>();

	// Project visibility ON and still ongoing
	boolean projectActive;

	public Manager(String nric, String password, UserRole role) {
		//super(nric, password, role);
		this.projectActive = false;
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
}
