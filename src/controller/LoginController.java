package controller;

import controller.user.ApplicantController;
import controller.user.ManagerController;
import controller.user.OfficerController;
import entity.user.Applicant;
import entity.user.Manager;
import entity.user.Officer;
import entity.user.User;

public class LoginController {
    public static User validateLogin(String nric, String password) {
        Officer officer = OfficerController.getOfficer(nric);
        if (officer != null && officer.getPassword().equals(password)) {
            return officer;
        }

        Manager manager = ManagerController.getManager(nric);
        if (manager != null && manager.getPassword().equals(password)) {
            return manager;
        }

        Applicant applicant = ApplicantController.getApplicant(nric);
        if (applicant != null && applicant.getPassword().equals(password)) {
            return applicant;
        }

        return null;    // None of the users
    }
}
