package interfaces;

import entity.user.Applicant;
import java.util.Map;

public interface IApplicantService {
    
    Applicant createApplicant(String nric, String name, int age, String maritalStatus, String password);
    
    Applicant getApplicant(String nric);
    
    Map<String, Applicant> getAllApplicants();
    
}
