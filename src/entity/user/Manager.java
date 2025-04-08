package entity.user;

import entity.project.BTOProject;
import enums.UserRole;
import java.util.HashMap;
import java.util.Map;

public class Manager extends User {
	// List of projects owned by manager
	private Map<String, BTOProject> managedProjects= new HashMap<>();

	// Project visibility ON and still ongoing
	boolean projectActive;

	public Manager(String nric, String name, String password, UserRole role, int age, String maritalStatus) {
		super(nric, name, password, age, maritalStatus);
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

	public void approveRegistration() {
		
	}
}
