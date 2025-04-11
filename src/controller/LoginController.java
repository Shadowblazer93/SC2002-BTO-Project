package controller;

import controller.user.ApplicantController;
import controller.user.ManagerController;
import controller.user.OfficerController;
import entity.user.Manager;
import entity.user.User;

public class LoginController {
    public static User validateLogin(String nric, String password) {
        ApplicantController applicantController = new ApplicantController();

        OfficerController officerController = new OfficerController();

        ManagerController managerController = new ManagerController();
        Manager manager = managerController.getManager(nric);
        if (manager != null && manager.getPassword().equals(password)) {
            return manager;
        }

        return null;    // None of the users
    }

    public static void changePassword() {
        
    }
}
