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

    public static boolean checkNRIC(String nric) {
        // Check if NRIC is valid (e.g., length, format)
        return nric != null && nric.length() == 9 && nric.matches("[ST]\\d{7}[A-Z]");
    }

    public static String strongPassword(String password) {
        if (password.length() < 8) {
            return "Password too short!";
        }
        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        String specialChar = "!*$?";
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (specialChar.indexOf(c) != -1) hasSpecial = true;
            else return "Password contains invalid character!";  // Invalid charactaer
        }
        if (hasLower && hasUpper && hasDigit && hasSpecial) {
            return "success";
        } else {
            return "Password not strong enough!";
        }
    }
}
