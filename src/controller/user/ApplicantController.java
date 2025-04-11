package controller.user;

import entity.user.Applicant;
import java.util.HashMap;
import java.util.Map;

public class ApplicantController {
    private static final Map<String, Applicant> allApplicants = new HashMap<>(); // NRIC + Applicant

    public Applicant createApplicant() {
        return null;
    }

    public Applicant getApplicant(String nric) {
        return allApplicants.get(nric);
    }

    public Map<String, Applicant> getAllApplicants() {
        return allApplicants;
    }
}
