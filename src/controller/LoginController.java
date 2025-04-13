package controller;

import controller.user.ApplicantController;
import controller.user.ManagerController;
import controller.user.OfficerController;
import entity.user.Applicant;
import entity.user.Manager;
import entity.user.User;

public class LoginController {
    public static User validateLogin(String nric, String password) {
        OfficerController officerController = new OfficerController();

        ManagerController managerController = new ManagerController();
        Manager manager = managerController.getManager(nric);
        if (manager != null && manager.getPassword().equals(password)) {
            return manager;
        }

        ApplicantController applicantController = new ApplicantController();
        Applicant applicant = applicantController.getApplicant(nric);
        if (applicant != null && applicant.getPassword().equals(password)) {
            return applicant;
        }

        return null;    // None of the users
    }
}
