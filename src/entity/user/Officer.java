package entity.user;

import entity.btoProject.BTOProject;

public class Officer extends User{
    private String officerName;
    private String officerNRIC;
    private BTOProject assignedProject;

    public officer (String username, String password, String officerName, String officerNRIC){
        super(username, password);
        this.officerName = officerName;
        this.officerNRIC = officerNRIC;
    }
    
    public BTOProject viewHandledProject(){
        return this.assignedProject;
    }

    //register officer to a project
    public void assignToProject(BTOProject project){
        this.assignedProject = project;
        project.assignOfficer(this);
    }


}