package entity.project;

import entity.user.Manager;
import enums.FlatType;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class BTOProject {
    private String projectName;
    private String neighbourhood;
    // Keeps track of flatTypes and numUnits
    private Map<String, Integer> unitCounts;
    private LocalDate openingDate;
    private LocalDate closingDate;
    private Manager managerInCharge;
    private int availableOfficerSlots;
    private boolean visible;

    public BTOProject(String projectName, Manager manager, String neighbourhood, int twoRooms, int threeRooms) {
        this.projectName = projectName;
        this.neighbourhood = neighbourhood;
        this.managerInCharge = manager;
        this.availableOfficerSlots = 10;    // Max 10
        this.unitCounts = new HashMap<>();
        unitCounts.put("2-room", twoRooms);
        unitCounts.put("3-room", threeRooms);
    }

    // Getters and setters

    public String getProjectName() {
        return projectName;
    }
    public String getNeighbourhood(){
        return neighbourhood;
    }
    public Map<String,Integer> getunitCounts(){
        return unitCounts;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }
}
