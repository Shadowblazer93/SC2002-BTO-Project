package enums;

/**
 * Represents the status of an applicant's application to a BTO Project
 */
public enum ApplicationStatus {

    /**
     * Application is pending and is awaiting approval/rejection
     */
    PENDING,

    /**
     * Application is approved and can be booked
     */
    SUCCESSFUL,

    /**
     * Application is rejected
     */
    UNSUCCESSFUL,

    /**
     * Booking of flat has been requested by applicant. Awaiting HDB Officer's confirmation of booking
     */
    PENDING_BOOKING,

    /**
     * Flat has been booked by HDB Officer
     */
    BOOKED,

    /**
     * Request of withdrawal of appolication has been aopproved
     */
    WITHDRAWN,
    
}
