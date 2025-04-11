package controller.user;

import entity.user.Officer;
import java.util.HashMap;
import java.util.Map;

public class OfficerController {
    private static final Map<String, Officer> allOfficers = new HashMap<>(); // NRIC + Officer

    public Officer createApplicant() {
        return null;
    }

    public Officer getApplicant(String nric) {
        return allOfficers.get(nric);
    }

    public Map<String, Officer> getAllOfficers() {
        return allOfficers;
    }
}
