package controller.user;

import entity.user.Applicant;

import java.util.HashMap;
import java.util.Map;

public class ApplicantController {
    private static final Map<String, Applicant> allApplicants = new HashMap<>(); // NRIC + Applicant

    public static Applicant createApplicant(String nric, String name, int age, String maritalStatus, String password) {
        Applicant applicant = new Applicant(nric,name,age,maritalStatus,password);
        allApplicants.put(nric, applicant);
        return applicant;
    }

    public static Applicant getApplicant(String nric) {
        return allApplicants.get(nric);
    }

    public static Map<String, Applicant> getAllApplicants() {
        return allApplicants;
    }
}
