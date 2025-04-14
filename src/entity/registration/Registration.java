package entity.registration;

import entity.user.Officer;
import enums.RegistrationStatus;
import java.time.LocalDate;

public class Registration {
    private final int id;
    private final Officer officer;
    private final String projectName;
    private final LocalDate registrationDate;
    private RegistrationStatus status;

    public Registration(int id, Officer officer, String projectName, LocalDate registrationDate, RegistrationStatus status) {
        this.id = id;
        this.officer = officer;
        this.projectName = projectName;
        this.registrationDate = registrationDate;
        this.status = status; 
    }

    public RegistrationStatus getRegistrationStatus() {
        return this.status;
    }

    public int getID() {
        return id;
    }

    public Officer getOfficer() {
        return this.officer;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public LocalDate getRegistrationDate() {
        return this.registrationDate;
    }

    public RegistrationStatus getStatus() {
        return this.status;
    }

    public void approveRegistration() {
        this.status = RegistrationStatus.APPROVED;
    }

    public void rejectRegistration() {
        this.status = RegistrationStatus.REJECTED;
    }
}
