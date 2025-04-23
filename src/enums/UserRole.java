package enums;


/**
 * Represents different roles a user can have in the BTOMS
 */
public enum UserRole {

	/**
	 * User who applies for BTO Projects
	 */
	APPLICANT,

	/**
	 * HDB Officer who manages enquiries and help applicants confirm their booking
	 * They can also apply to projects as applicant
	 */
	OFFICER,

	/**
	 * HDB Manager who manages BTO Projects, applications, enquiries and officer registrations
	 */
	MANAGER,
	
}
