package interfaces;

import entity.application.BTOApplication;
import entity.project.BTOProject;
import entity.user.Manager;
import entity.user.User;
import enums.FlatType;
import java.time.LocalDate;
import java.util.Map;

public interface IProjectService {

    Map<String, BTOProject> getAllProjects();

    BTOProject getProjectByName(String projectName);

    BTOProject createProject(Manager manager, String projectName, String neighbourhood, 
                            Map<FlatType, Integer> unitCounts, Map<FlatType, Double> unitPrices, 
                            LocalDate openingDate, LocalDate closingDate, int availableOfficerSlots);

    boolean deleteProject(Manager manager, String projectName);

    boolean projectExist(String projectName);

    boolean isProjectOpen(BTOProject project);

    boolean flatTypeAvailable(BTOProject project, FlatType flatType);
    
    boolean checkOverlapPeriod(Manager manager, String projectName, LocalDate openingDate, LocalDate closingDate);

    boolean bookFlat(BTOApplication application, BTOProject project, FlatType flatType, User user);
    
    boolean editProjectName(Manager manager, String currentName, String newName, BTOProject projectEdit);
    
    boolean editNeighbourhood(String neighbourhood, BTOProject projectEdit);

    boolean editNumUnits(FlatType flatType, int numUnits, BTOProject projectEdit);

    boolean editPrice(FlatType flatType, Double price, BTOProject projectEdit);

    boolean editOfficerSlots(int officerSlots, BTOProject projectEdit);

    boolean editOpeningDate(LocalDate openingDate, BTOProject projectEdit);

    boolean editClosingDate(LocalDate closingDate, BTOProject projectEdit);

    boolean editVisibility(boolean visible, BTOProject projectEdit);

} 
