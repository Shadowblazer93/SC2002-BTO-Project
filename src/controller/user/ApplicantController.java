package controller.user;

import entity.user.Applicant;
import interfaces.IApplicantService;
import java.util.HashMap;
import java.util.Map;

public class ApplicantController implements IApplicantService {
    private static final Map<String, Applicant> allApplicants = new HashMap<>(); // NRIC + Applicant

    @Override
    public Applicant createApplicant(String nric, String name, int age, String maritalStatus, String password) {
        Applicant applicant = new Applicant(nric,name,age,maritalStatus,password);
        allApplicants.put(nric, applicant);
        return applicant;
    }
    
    @Override
    public Applicant getApplicant(String nric) {
        return allApplicants.get(nric);
    }

    @Override
    public Map<String, Applicant> getAllApplicants() {
        return allApplicants;
    }
}
