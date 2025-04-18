package interfaces;

import entity.project.BTOProject;
import entity.registration.Registration;
import entity.user.Manager;
import entity.user.Officer;
import enums.RegistrationStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IRegistrationService {

    Map<String, List<Registration>> getAllRegistrations();
    
    List<Registration> getRegistrationsByProject(String projectName);
    
    void addRegistration(String project, Registration registration);

    Registration createRegistration(int id, Officer officer, String projectName, 
                                    LocalDate registrationDate, RegistrationStatus status);
                                    
    Registration registerProject(Officer officer, BTOProject project);

    String approveRegistration(Manager manager, Registration registration);
    
    String rejectRegistration(Manager manager, Registration registration);

}
