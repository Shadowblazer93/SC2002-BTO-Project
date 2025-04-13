package entity.user;

import entity.project.BTOProject;
import enums.UserRole;
import java.util.HashMap;
import java.util.Map;

public class Manager extends User {
	// List of projects owned by manager
	private Map<String, BTOProject> managedProjects= new HashMap<>();
	private BTOProject currentProject;	// Current project handled by manager

	// Project visibility ON and still ongoing
	boolean projectActive;

	public Manager(String nric, String name, String password, int age, String maritalStatus) {
		super(nric, name, password, age, maritalStatus, UserRole.MANAGER);
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
<<<<<<< HEAD

	public BTOProject getCurrentProject() {
		return currentProject;
	}
=======
	
	
>>>>>>> 4583d49e0b693d620c54d17a58f08f8c9e97f0dc
}
