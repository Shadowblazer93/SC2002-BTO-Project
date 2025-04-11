package entity.user;

import entity.project.BTOProject;
import enums.UserRole;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager extends User {
	// List of projects owned by manager
	private Map<String, BTOProject> managedProjects= new HashMap<>();

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
	public void viewPendingApplications(BTOProject project) {
		System.out.println("Pending officer applications for project: " + project.getProjectName());
		List<Officer> pending = project.getPendingApplicants();
		if (pending.isEmpty()) {
			System.out.println("No pending applications.");
		} else {
			for (Officer officer : pending) {
				System.out.println("- " + officer.getName());
			}
		}
	}
	public void approveOfficer(BTOProject project, Officer officer) {
    if (project.getPendingApplicants().contains(officer)) {
        project.assignOfficer(officer);             // actual assignment logic
        officer.assignProject(project);             // update officer's side
        project.getPendingApplicants().remove(officer);
        System.out.println("Approved and assigned " + officer.getName() + " to project " + project.getProjectName());
    } else {
        System.out.println("This officer did not apply for this project.");
    }
}



	public void approveRegistration() {
		
	}
}
