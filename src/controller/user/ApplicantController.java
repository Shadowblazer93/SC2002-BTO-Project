package controller.user;

import entity.user.Applicant;

import java.util.HashMap;
import java.util.Map;

public class ApplicantController {
    private static final Map<String, Applicant> allApplicants = new HashMap<>(); // NRIC + Applicant

    public Applicant createApplicant(String nric, String name, int age, String maritalStatus, String password) {
        Applicant applicant = new Applicant(nric,name,age,maritalStatus,password);
        allApplicants.put(nric, applicant);
        return applicant;
    }

    public Applicant getApplicant(String nric) {
        return allApplicants.get(nric);
    }

    public Map<String, Applicant> getAllApplicants() {
        return allApplicants;
    }

    public void updateApplicantStatus() {   // (In Officer)
        
    }

    public void updateApplicantProfile() {  // (In Officer)

    }
}
