package entity.project;

import entity.enquiry.Enquiry;
import entity.user.Manager;
import enums.FlatType;
import java.time.LocalDate;
import java.util.Map;

public class BTOProject {
    private String projectName;
    private String neighbourhood;
    // Keeps track of flatTypes and numUnits
    private Map<FlatType, Integer> unitCounts;
    private LocalDate openingDate;
    private LocalDate closingDate;
    private Manager managerInCharge;
    private int availableOfficerSlots;
    private boolean visible;
    private Enquiry[] enquiries; 

    public BTOProject(String projectName, Manager manager, String neighbourhood, 
                        Map<FlatType, Integer> unitCounts, LocalDate openingDate, LocalDate closingDate,
                        int availableOfficerSlots) {
        this.projectName = projectName;
        this.neighbourhood = neighbourhood;
        this.managerInCharge = manager;
        this.availableOfficerSlots = 10;    // Max 10
        this.unitCounts = unitCounts; 
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.availableOfficerSlots = availableOfficerSlots;
        this.visible = false;
        //unitCounts.put("2-room", twoRooms);
        //unitCounts.put("3-room", threeRooms);
    }

    // Getters and setters

    public String getProjectName() {
        return projectName;
    }

    public Enquiry[] getEnquiries() {
        return this.enquiries; // Returns the array of enquiries
    }
    public String getNeighbourhood(){
        return neighbourhood;
    }
    public Map<FlatType,Integer> getunitCounts(){
        return unitCounts;
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
        return "BTO Project Name: " + projectName + ", Neighbourhood: " + neighbourhood + ", Units Available: " + numUnits;
    }
}
