package entity.project;

import entity.application.BTOApplication;
import entity.enquiry.Enquiry;
import entity.registration.Registration;
import entity.user.Manager;
import entity.user.Officer;
import enums.FlatType;
import java.time.LocalDate;
import java.util.*;

public class BTOProject {
    private String projectName;
    private String neighbourhood;
    private Map<FlatType, Integer> unitCounts;          // Track of flatTypes and numUnits
    private LocalDate openingDate;
    private LocalDate closingDate;
    private final Manager managerInCharge;                  // Manager in charge does not change
    private int availableOfficerSlots;
    private boolean visible;
    private Map<Integer, Enquiry> enquiries;                // Map of enquiries (ID, Enquiry)
    private Map<String, BTOApplication> applications;       // Applications for project (NRIC, Application)
    private List<Officer> assignedOfficers;                 // List of officers assigned to project
    private Map<String, Registration> registrations;        // Map of registrations (NRIC, Registration)

    public BTOProject(String projectName, Manager manager, String neighbourhood, 
                        Map<FlatType, Integer> unitCounts, LocalDate openingDate, LocalDate closingDate,
                        int availableOfficerSlots) {
        this.projectName = projectName;
        this.neighbourhood = neighbourhood;
        this.managerInCharge = manager;
        this.unitCounts = unitCounts; 
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.availableOfficerSlots = availableOfficerSlots;
        this.visible = false;
        this.enquiries = new HashMap<>();
        this.applications = new HashMap<>();
        this.assignedOfficers = new ArrayList<>();
        this.registrations = new HashMap<>();
    }

    public void addOfficer(Officer officer) {
        this.assignedOfficers.add(officer);   
    }

    // Assign officer to project
    public void assignOfficer(Registration registration) {
        Officer officer = registration.getOfficer();
        addOfficer(officer);
        this.availableOfficerSlots--;
    }

    // Add officer to list of registrations
    public void addRegistration(Registration registration) {
        Officer officer = registration.getOfficer();
        registrations.put(officer.getNRIC(), registration);  
    }

    // Add application to project
    public void addApplication(BTOApplication application) {
        String nric = application.getApplicant().getNRIC();
        applications.put(nric, application);
    }

    public void addEnquiry(Enquiry enquiry) {
        enquiries.put(enquiry.getID(), enquiry); // Add enquiry to the map
    }

    // Decrement flat count
    public boolean decrementFlatCount(FlatType flatType) {
        int count = unitCounts.get(flatType);
        if (count > 0) {
            unitCounts.put(flatType, count - 1);
            return true;
        }
        return false; // No units available
    }

    // Getters and setters

    public String getProjectName() {
        return projectName;
    }

    public Map<Integer, Enquiry> getEnquiries() {
        return this.enquiries;
    }
    public String getNeighbourhood(){
        return neighbourhood;
    }
    public Map<FlatType,Integer> getunitCounts(){
        return unitCounts;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public Manager getManager() {
        return managerInCharge;
    }

    public int getAvailableOfficerSlots() {
        return availableOfficerSlots;
    }

    public boolean getVisibility() {
        return visible;
    }

    public List<Officer> getAssignedOfficers() {
        return assignedOfficers;
    }

    public Map<String, Registration> getRegistrations() {
        return registrations;
    }

    public Map<String, BTOApplication> getApplications() {
        return applications;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public void setNumUnits(FlatType flatType, int numUnits) {
        this.unitCounts.put(flatType, numUnits);
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BTO Project Details\n")
        .append("Project Name           : ").append(projectName).append("\n")
        .append("Neighbourhood          : ").append(neighbourhood).append("\n")
        .append("Manager In Charge      : ").append(managerInCharge.getName()).append("\n")
        .append("Opening Date           : ").append(openingDate).append("\n")
        .append("Closing Date           : ").append(closingDate).append("\n")
        .append("Available Officer Slots: ").append(availableOfficerSlots).append("\n")
        .append("Visibility             : ").append(visible ? "Visible" : "Not Visible").append("\n")
        .append("Unit Counts: \n");

        for (Map.Entry<FlatType, Integer> entry : unitCounts.entrySet()) {
            sb.append(entry.getKey().getNumRooms()).append("-Room: ").append(entry.getValue()).append(" units\n");
        }

        return sb.toString();
    }

    // Getter
    public Map<FlatType, Integer> getUnitCounts() {
        return unitCounts;
    }

    // Setter (optional)
    public void setUnitCounts(Map<FlatType, Integer> unitCounts) {
        this.unitCounts = unitCounts;
    }

    public boolean isVisible() {
        return visible;
    }
}
