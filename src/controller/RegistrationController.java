package controller;

import entity.registration.Registration;
import java.util.ArrayList;
import java.util.List;

public class RegistrationController {
    List<Registration> allRegistrations;

    public RegistrationController() {
        allRegistrations = new ArrayList<>();
    }

    public void addRegistration(Registration registration) {
        allRegistrations.add(registration);
    }
}
