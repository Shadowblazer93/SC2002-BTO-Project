package entity.registration;

import entity.project.BTOProject;
import entity.user.Officer;
import java.time.LocalDate;

public class Registration {
    private final Officer officer;
    private final BTOProject project;
    private final LocalDate registrationDate;
    private boolean approved;

    public Registration(Officer officer, BTOProject project, LocalDate registrationDate) {
        this.officer = officer;
        this.project = project;
        this.registrationDate = registrationDate;
        this.approved = false; 
    }

    public boolean getRegistrationStatus() {
        return this.approved;
    }

    public void setStatus(boolean approved) {
        this.approved = approved;
    }
}
