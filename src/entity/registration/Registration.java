package entity.registration;

import entity.project.BTOProject;
import entity.user.Officer;
import enums.RegistrationStatus;
import java.time.LocalDate;

public class Registration {
    private final Officer officer;
    private final BTOProject project;
    private final LocalDate registrationDate;
    private RegistrationStatus status;

    public Registration(Officer officer, BTOProject project, LocalDate registrationDate) {
        this.officer = officer;
        this.project = project;
        this.registrationDate = registrationDate;
        this.status = RegistrationStatus.PENDING; 
    }

    public RegistrationStatus getRegistrationStatus() {
        return this.status;
    }

    public void approveRegistration() {
        this.status = RegistrationStatus.APPROVED;
    }

    public void rejectRegistration() {
        this.status = RegistrationStatus.REJECTED;
    }
}
