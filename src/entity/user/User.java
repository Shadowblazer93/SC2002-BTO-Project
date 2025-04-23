package entity.user;

import enums.UserRole;

/**
 * User class represents a generic user in the BTO system.
 * Stores common attributes and methods shared by all user types, such as NRIC, name, password, age, marital status, and user role.
 */
public class User {
    private String nric;
    private String name;
    private String password;
    private int age;
    private String maritalStatus;
    private UserRole userRole;

    /**
     * Constructs a User object with the specified details.
     *
     * @param nric          NRIC of the user.
     * @param name          Name of the user.
     * @param password      Password for the user's account.
     * @param age           Age of the user.
     * @param maritalStatus Marital status of the user (e.g., "single", "married").
     * @param userRole      Role of the user in the system (e.g., APPLICANT, OFFICER, MANAGER).
     */
    public User(String nric, String name, String password, int age, String maritalStatus, UserRole userRole) {
        this.nric = nric;
        this.name = name;
        this.password = password;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.userRole = userRole;
    }

    /**
     * Sets the password for the user.
     *
     * @param password New password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the user role for the user.
     *
     * @param userRole New user role to set.
     */
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    /**
     * Retrieves the NRIC of the user.
     *
     * @return NRIC of the user.
     */
    public String getNRIC() {
        return nric;
    }

    /**
     * Retrieves the name of the user.
     *
     * @return Name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return Password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the age of the user.
     *
     * @return Age of the user.
     */
    public int getAge() {
        return age;
    }

    /**
     * Retrieves the marital status of the user.
     *
     * @return Marital status of the user.
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Retrieves the user role of the user.
     *
     * @return User role of the user.
     */
    public UserRole getUserRole() {
        return userRole;
    }
}