package enums;

/**
 * Represents the status of an enquiry
 */
public enum EnquiryStatus {

    /**
     * Enquiry has not been replied to and can still be edited/deleted
     */
    OPEN,

    /**
     * Enquiry has been replied to and cannot be edited/deleted
     */
    CLOSED;
    
}
