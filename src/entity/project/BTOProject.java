package entity.project;

import entity.application.BTOApplication;
import entity.enquiry.Enquiry;
import entity.registration.Registration;
import entity.user.Manager;
import entity.user.Officer;
import enums.FlatType;
import enums.defColor;
import java.time.LocalDate;
import java.util.*;

/**
 * Represents a HDB BTO project.
 * It stores details of the project including the list of applications, enquiries, assigned officers and registrations
 */
public class BTOProject {

    private String projectName;
    private String neighbourhood;
    private Map<FlatType, Integer> unitCounts;          // Track of flatTypes and numUnits
    private Map<FlatType, Double> unitPrices;
    private LocalDate openingDate;
    private LocalDate closingDate;
    private final Manager managerInCharge;                  // Manager in charge does not change
    private int availableOfficerSlots;
    private boolean visible;
    private Map<Integer, Enquiry> enquiries;                // Map of enquiries (ID, Enquiry)
    private Map<String, BTOApplication> applications;       // Applications for project (NRIC, Application)
    private List<Officer> assignedOfficers;                 // List of officers assigned to project
    private Map<String, Registration> registrations;        // Map of registrations (NRIC, Registration)

    /**
     * Construct BTOProject with specified details
     * @param projectName Name of BTO Project
     * @param manager Manager in charge of the project
     * @param neighbourhood Project location
     * @param unitCounts Map of flat types and available unit counts
     * @param unitPrices Map of flat types and prices
     * @param openingDate Project opening date for applications
     * @param closingDate Project closing date for applications
     * @param availableOfficerSlots Number of officers that can be assigned
     */
    public BTOProject(String projectName, Manager manager, String neighbourhood, 
                        Map<FlatType, Integer> unitCounts, Map<FlatType, Double> unitPrices, 
                        LocalDate openingDate, LocalDate closingDate, int availableOfficerSlots) {
        this.projectName = projectName;
        this.neighbourhood = neighbourhood;
        this.managerInCharge = manager;
        this.unitCounts = unitCounts; 
        this.unitPrices = unitPrices;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.availableOfficerSlots = availableOfficerSlots;
        this.visible = false;
        this.enquiries = new HashMap<>();
        this.applications = new HashMap<>();
        this.assignedOfficers = new ArrayList<>();
        this.registrations = new HashMap<>();
    }

    /**
     * Check if the project is open for applications
     * @return true if project is open, false otherwise
     */
    public boolean isOpen() {
        LocalDate today = LocalDate.now();
        return openingDate.isBefore(today) && closingDate.isAfter(today);
    }

    /**
     * Adds officer to the assigned officer list
     * @param officer Officer to be added
     */
    public void addOfficer(Officer officer) {
        this.assignedOfficers.add(officer);   
    }

    /**
     * Assign officer to this project through a registration
     * @param registration Officer registration for the project
     */
    public void assignOfficer(Registration registration) {
        Officer officer = registration.getOfficer();
        addOfficer(officer);
        this.availableOfficerSlots--;
    }

    /**
     * Register an officer for the project
     * @param registration Officer's registration
     */
    public void addRegistration(Registration registration) {
        registrations.put(registration.getOfficer().getNRIC(), registration);  
    }

    /**
     * Add an applicant's application to the project
     * @param application BTO Application to be added
     */
    public void addApplication(BTOApplication application) {
        String nric = application.getApplicant().getNRIC();
        applications.put(nric, application);
    }

    /**
     * Add enquiry related to the project
     * @param enquiry Enquiry to add
     */
    public void addEnquiry(Enquiry enquiry) {
        enquiries.put(enquiry.getID(), enquiry); // Add enquiry to the map
    }

    /**
     * Decrease the available count of the specified flat type by 1
     * @param flatType Type of flat to decrement
     * @return true if successful, false if no units available
     */
    public boolean decrementFlatCount(FlatType flatType) {
        int count = unitCounts.get(flatType);
        if (count > 0) {
            unitCounts.put(flatType, count - 1);
            return true;
        }
        return false; // No units available
    }

    // Getters and setters

    /**
     * Get project name
     * @return Project name
     */
    public String getProjectName() {
        return projectName;
    }
    
    /**
     * Get project neighbourhood
     * @return Project neighbourhood
     */
    public String getNeighbourhood(){
        return neighbourhood;
    }

    /**
     * Get map of Flat Type and unit counts
     * @return Map of flat type and unit counts
     */
    public Map<FlatType,Integer> getUnitCounts(){
        return unitCounts;
    }

    /**
     * Get map of Flat Type and unit prices
     * @return Map of flat type and unit prices
     */
    public Map<FlatType, Double> getUnitPrices() {
        return unitPrices;
    }

    /**
     * Get project application opening date
     * @return Project application opening date
     */
    public LocalDate getOpeningDate() {
        return openingDate;
    }

    /**
     * Get project application closing date
     * @return Project application closing date
     */
    public LocalDate getClosingDate() {
        return closingDate;
    }

    /**
     * Get manager in charge of project
     * @return Manger in charge of project
     */
    public Manager getManager() {
        return managerInCharge;
    }

    /**
     * Get number of available officer slots for the project
     * @return Available officer slots
     */
    public int getAvailableOfficerSlots() {
        return availableOfficerSlots;
    }

    /**
     * Get the visibility status of the project
     * @return true if project is visible, false otherwise
     */
    public boolean getVisibility() {
        return visible;
    }

    /**
     * Get the list of officers assigned to project
     * @return List of officers assigned to project
     */
    public List<Officer> getAssignedOfficers() {
        return assignedOfficers;
    }

    /**
     * Get the list of registrations for the project
     * @return List of registrations
     */
    public Map<String, Registration> getRegistrations() {
        return registrations;
    }

    /**
     * Get the map of enquiry ID and enquiries related to the project
     * @return Map of enquiry ID and enquiries
     */
    public Map<Integer, Enquiry> getEnquiries() {
        return this.enquiries;
    }

    /**
     * Get the map of applicant NRIC and applications for the project
     * @return Map of applicant NRIC and applications
     */
    public Map<String, BTOApplication> getApplications() {
        return applications;
    }

    /**
     * Set the project name
     * @param projectName Project name
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Set the project neighbourhood
     * @param neighbourhood New neigbourhood
     */
    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    /**
     * Set the number of units for the specified flat type in the project
     * @param flatType Flat type to set
     * @param numUnits New number of units
     */
    public void setNumUnits(FlatType flatType, int numUnits) {
        this.unitCounts.put(flatType, numUnits);
    }

    /**
     * Set the price of the specified flat type in the projcet
     * @param flatType Flat type to set
     * @param price New price
     */
    public void setPrice(FlatType flatType, double price) {
        this.unitPrices.put(flatType, price);
    }

    /**
     * Set the application opening date of the projcet
     * @param openingDate New opening date
     */
    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    /**
     * Set the application closing date of the project
     * @param closingDate New closing date
     */
    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    /**
     * Set the number of available officers that can be assigned to projecta
     * @param availableOfficerSlots New number of officer slots
     */
    public void setAvailableOfficerSlots(int availableOfficerSlots) {
        this.availableOfficerSlots = availableOfficerSlots;
    }

    /**
     * Set the visibility of project
     * @param visible true if visible, false otherwise
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Returns string representation of BTO Projkect
     * @return Formatted project details
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(defColor.YELLOW).append("BTO Project Details\n")
        .append(defColor.YELLOW).append("Project Name           : ").append(projectName).append("\n")
        .append(defColor.YELLOW).append("Neighbourhood          : ").append(neighbourhood).append("\n")
        .append(defColor.YELLOW).append("Manager In Charge      : ").append(managerInCharge.getName()).append("\n")
        .append(defColor.YELLOW).append("Opening Date           : ").append(openingDate).append("\n")
        .append(defColor.YELLOW).append("Closing Date           : ").append(closingDate).append("\n")
        .append(defColor.YELLOW).append("Available Officer Slots: ").append(availableOfficerSlots).append("\n")
        .append(defColor.YELLOW).append("Visibility             : ").append(visible ? "Visible" : "Not Visible").append("\n")
        .append(defColor.YELLOW).append("Unit Counts: \n");

        for (Map.Entry<FlatType, Integer> entry : unitCounts.entrySet()) {
            sb.append(entry.getKey().getNumRooms()).append("-Room: ").append(entry.getValue()).append(" units\n");
        }

        return sb.toString();
    }
}
