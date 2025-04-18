package interfaces;

import entity.application.BTOApplication;
import entity.project.BTOProject;
import entity.user.Applicant;
import entity.user.Officer;
import enums.ApplicationStatus;
import enums.FlatType;
import java.util.Map;

public interface IApplicationService {

    void addApplication(BTOApplication application);
    
    void removeApplication(BTOApplication application);
    
    Map<String, BTOApplication> getAllApplications();
    
    BTOApplication getApplicationByNRIC(String nric);

    BTOApplication createApplication(int id, Applicant applicant, String projectName, 
                                    FlatType flatType, ApplicationStatus status, boolean withdrawal);

    boolean isEligible(Applicant applicant, FlatType flatType);

    BTOApplication applyProject(Applicant applicant, BTOProject project, FlatType flatType);

    void requestWithdrawal(Applicant applicant);

    void approveApplication(BTOApplication application);

    void rejectApplication(BTOApplication application);

    void approveWithdrawal(BTOApplication application);

    void rejectWithdrawal(BTOApplication application);

    boolean hasAccessToApplication(Officer officer, BTOApplication application);
}
