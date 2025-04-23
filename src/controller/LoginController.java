package controller;

import entity.user.Applicant;
import entity.user.Manager;
import entity.user.Officer;
import entity.user.User;
import interfaces.IApplicantService;
import interfaces.ILoginService;
import interfaces.IManagerService;
import interfaces.IOfficerService;

/**
 * This class handles login validation and authentication logic for users in the system
 */
public class LoginController implements ILoginService {

    private final IApplicantService applicantService;
    private final IOfficerService officerService;
    private final IManagerService managerService;

    /**
     * Construct LoginController with the services for applicant, officer and manager
     * @param applicantService Service handling applicant-related operations
     * @param officerService Service handling officer-related operations
     * @param managerService Service handling manager-related operations
     */
    public LoginController(IApplicantService applicantService, IOfficerService officerService, IManagerService managerService) {
        this.applicantService = applicantService;
        this.officerService = officerService;
        this.managerService = managerService;
    }

    /**
     * Validates login credentials of a user by checking the NRIC and password
     * @param nric NRIC of user attempting login
     * @param password Password provided by user
     * @return {@link User} object if credentials are valid, {@code null} otherwise
     */
    @Override
    public User validateLogin(String nric, String password) {
        Officer officer = officerService.getOfficer(nric);
        if (officer != null && officer.getPassword().equals(password)) {
            return officer;
        }

        Manager manager = managerService.getManager(nric);
        if (manager != null && manager.getPassword().equals(password)) {
            return manager;
        }

        Applicant applicant = applicantService.getApplicant(nric);
        if (applicant != null && applicant.getPassword().equals(password)) {
            return applicant;
        }

        return null; 
    }

    /**
     * Check if the provided NRIC is in a valid format
     * Valid NRIC must start with 'S' or 'T', followed by 7 digits and end with a letter
     * @param nric NRIC string to validate
     * @return true if NRIC format is valid, false otherwise
     */
    @Override
    public boolean checkNRIC(String nric) {
        // Check if NRIC is valid (e.g., length, format)
        return nric != null && nric.length() == 9 && nric.matches("[ST]\\d{7}[A-Z]");
    }

    /**
     * Validates strength of new password
     * Strong password musat 
     *  - Be at least 8 characters long
     *  - Contain lowercase and uppercase letters
     *  - Include at least 1 digit
     *  - Include one of the special characters (!, *, $, ?)
     *  - Not conatin any other characters
     * @param password Password string to validate
     * @return "success" if password is strong, otherwise an error message indicating issue
     */
    @Override
    public String strongPassword(String password) {
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
