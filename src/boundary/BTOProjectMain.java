package boundary;

import controller.BTOProjectController;
import entity.project.BTOProject;
import entity.user.Manager;
import enums.FlatType;
import enums.defColor;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import printer.PrintBTOProjects;

/**
 * Boundary class for managing BTO projects
 * This class handles the user interface for project management
 */
public class BTOProjectMain {
    PrintBTOProjects printer = new PrintBTOProjects();

    /**
     * Display the project management menu for the manager
     * @param manager Manager using the menu
     * @param sc Scanner object for user input
     */
    public void displayMenu(Manager manager, Scanner sc) {
        boolean running = true;
        while (running) { 
            System.out.print(defColor.PURPLE + """
                ===============================
                    Project Management Menu
                -------------------------------
                """ + defColor.BLUE +
                """
                1. Create project
                2. Edit project
                3. Delete project
                4. View all projects
                5. View my projects
                6. View current project details
                7. Exit
                """ + defColor.PURPLE + 
                "===============================\n" + defColor.RESET);
            int choice = 0;
            boolean validInput = false;
            while (!validInput) {
                try {
                    System.out.print("Option: ");
                    choice = sc.nextInt();
                    sc.nextLine();
                    validInput = true;
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine(); 
                }
            }

            switch (choice) {
                case 1 -> {
                    createProject(manager, sc);
                }
                case 2 -> {
                    editProject(manager, sc);
                    break;
                }
                case 3 -> {
                    deleteProject(manager, sc);
                    break;
                }
                case 4 -> {
                    printer.printMap(BTOProjectController.getAllProjects());
                    break;
                }
                case 5 -> {
                    printer.printMap(manager.getManagedProjects());
                    break;
                }
                case 6 -> {
                    viewCurrentProject(manager);
                }
                case 7 -> {
                    System.out.println("Exiting project management menu.");
                    running = false;
                }
                default -> {
                    System.out.println("Invalid option");
                }
            }
        }
    }

    /**
     * Display details of the current project managed by the manager
     * @param manager Manager viewing the project
     */
    private void viewCurrentProject(Manager manager) {
        if (manager.getCurrentProject() == null) {
            System.out.println("You are currently not managing any project.");
            return;
        }
        BTOProject currentProject = manager.getCurrentProject();
        System.out.println(currentProject.toString());
    }

    /**
     * Handle input details for creation of a new project
     * @param manager Manager creating the project
     * @param sc Scanner object for user input
     */
    private void createProject(Manager manager, Scanner sc) {
        // Project name
        System.out.print("Project name: ");
        String projectName = sc.nextLine();
        if (BTOProjectController.projectExist(projectName)) {
            System.out.println("Project already exists.");
            return;
        }
        // Project neighbourhood
        System.out.print("Project neighbourhood: ");
        String neighbourhood = sc.nextLine();
        // Types of Flat + Number of units + Price of unit
        Map<FlatType, Integer> unitCounts = new HashMap<>();
        Map<FlatType, Double> unitPrices = new HashMap<>();
        for (FlatType flatType : FlatType.values()) {
            int units = -1;
            while (units < 0) {
                try {
                    System.out.printf("Number of units for %d-Room flats: ", flatType.getNumRooms());
                    units = sc.nextInt();
                    if (units < 0) {
                        System.out.println("Number of units cannot be less than 0.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine(); 
                } 
            }
            sc.nextLine();
            unitCounts.put(flatType, units);
            Double price = -1.0;
            while (price < 0) {
                try {
                    System.out.printf("Price of %d-Room flats: ", flatType.getNumRooms());
                    price = sc.nextDouble();
                    if (price < 0) {
                        System.out.println("Price cannot be less than 0.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine(); 
                } 
            }
            sc.nextLine();
            unitPrices.put(flatType, price);
        }
        // Application period
        boolean correctPeriod = false;
        LocalDate oDate = null;
        LocalDate cDate = null;
        while (!correctPeriod) {
            // Application opening date
            while (oDate == null) {
                System.out.print("Application opening date (YYYY-MM-DD): ");
                String oDateInput = sc.nextLine();
                try {
                    oDate = LocalDate.parse(oDateInput);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date.");
                }
            }
            // Application closing date
            while (cDate == null) {
                System.out.print("Application closing date (YYYY-MM-DD): ");
                String cDateInput = sc.nextLine();
                try {
                    cDate = LocalDate.parse(cDateInput);
                    if (cDate.isBefore(oDate)) {
                        System.out.println("Closing date must be after opening date.");
                        cDate = null;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date.");
                }
            }
            correctPeriod = !BTOProjectController.checkOverlapPeriod(manager, null, oDate, cDate);
            if (!correctPeriod) {
                System.out.println("Project overlaps with existing project you manage. Please enter a different date.");
                oDate = null;
                cDate = null;
            }
        }
        // Available HDB Office Slots
        int slots = 0;
        while (slots <= 0) {
            try {
                System.out.printf("Available HDB Office Slots (Max 10): ");
                slots = sc.nextInt();
                if (slots <= 0) {
                    System.out.println("Number of slots must be greater than 0.");
                } else if (slots > 10) {
                    System.out.println("Number of slots must not exceed 10.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); 
            } 
        }
        // Create project
        BTOProject createdProject = BTOProjectController.createProject(manager, projectName, neighbourhood, unitCounts, unitPrices, oDate, cDate, slots);
        if (createdProject == null) {
            System.out.println("Could not create project.");
        } else {
            System.out.printf("Project %s successfully created!\n", projectName);
            System.out.println(createdProject.toString());
        }
    }

    /**
     * Edit an existing project managed by the manager
     * @param manager Manager editing the project
     * @param sc Scanner object for user input
     */
    private void editProject(Manager manager, Scanner sc) {
        if (manager.getManagedProjects().isEmpty()) {
            System.out.println("No projects to edit.");
            return;
        }
        printer.printMap(manager.getManagedProjects());
        System.out.print("Enter name of project to edit: ");
        String projectName = sc.nextLine();

        // Check if project exists
        BTOProject projectEdit = manager.getManagedProjects().get(projectName);
        if (projectEdit == null) {
            System.out.println("Project not found.");
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println(defColor.BLUE + """
            What would you like to edit?
            1. Project Name
            2. Project neighbourhood
            3. Number of units
            4. Unit Prices
            5. Application opening date
            6. Application closing date
            7. Available HDB Officer Slots
            8. Toggle Visibility
            9. Exit edit project menu
                """ + defColor.RESET);
            System.out.print("(Enter number) Attribute to edit: ");
            int choice = sc.nextInt();
            sc.nextLine();
            boolean edited = false;
            switch (choice) {
                case 1 -> {
                    System.out.print("New project name: ");
                    String newProjectName = sc.nextLine();
                    edited = BTOProjectController.editProjectName(manager, projectName, newProjectName, projectEdit);
                }
                case 2 -> {
                    System.out.print("New neighbourhood: ");
                    String newNeighbourhood = sc.nextLine();
                    edited = BTOProjectController.editNeighbourhood(newNeighbourhood, projectEdit);
                }
                case 3 -> {
                    System.out.print("Flat type to edit (2 or 3): ");
                    int flatTypeInput = sc.nextInt();
                    FlatType flatType = FlatType.getFlatType(flatTypeInput);
                    if (flatType == null) {
                        System.out.println("Invalid flat type.");
                    }
                    int newNumUnits = -1;
                    while (newNumUnits < 0) {
                        try {
                            System.out.print("New number of units: ");
                            newNumUnits = sc.nextInt();
                            if (newNumUnits < 0) {
                                System.out.println("Number of units cannot be less than 0.");
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid input. Please enter a number.");
                            sc.nextLine(); 
                        }
                    }
                    edited = BTOProjectController.editNumUnits(flatType, newNumUnits, projectEdit);
                }
                case 4 -> {
                    System.out.print("Flat type to edit (2 or 3): ");
                    int flatTypeInput = sc.nextInt();
                    FlatType flatType = FlatType.getFlatType(flatTypeInput);
                    if (flatType == null) {
                        System.out.println("Invalid flat type.");
                    }
                    Double newPrice = -1.1;
                    while (newPrice < 0) {
                        try {
                            System.out.print("New price: ");
                            newPrice = sc.nextDouble();
                            if (newPrice < 0) {
                                System.out.println("Price cannot be less than 0.");
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid input. Please enter a number.");
                            sc.nextLine(); 
                        }
                    }
                    edited = BTOProjectController.editPrice(flatType, newPrice, projectEdit);
                }
                case 5 -> {
                    boolean valid = false;
                    LocalDate oDate = null;
                    while (!valid) {
                        System.out.print("New application opening date (YYYY-MM-DD): ");
                        String oDateInput = sc.nextLine();
                        try {
                            oDate = LocalDate.parse(oDateInput);
                            valid = !BTOProjectController.checkOverlapPeriod(manager, projectEdit.getProjectName(), oDate, projectEdit.getClosingDate());
                            if (!valid) {
                                System.out.println("Project overlaps with existing project you manage. Please enter a different date.");
                                oDate = null;
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date.");
                        }
                    }
                    edited = BTOProjectController.editOpeningDate(oDate, projectEdit);
                }
                case 6 -> {
                    boolean valid = false;
                    LocalDate cDate = null;
                    while (!valid) {
                        System.out.print("New application closing date (YYYY-MM-DD): ");
                        String cDateInput = sc.nextLine();
                        try {
                            cDate = LocalDate.parse(cDateInput);
                            valid = !BTOProjectController.checkOverlapPeriod(manager, projectEdit.getProjectName(), projectEdit.getOpeningDate(), cDate);
                            if (!valid) {
                                System.out.println("Project overlaps with existing project you manage. Please enter a different date.");
                                cDate = null;
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date.");
                        }
                    }
                    edited = BTOProjectController.editClosingDate(cDate, projectEdit);
                }
                case 7 -> {
                    System.out.print("New available HDB office slots: ");
                    int slots = sc.nextInt();
                    edited = BTOProjectController.editOfficerSlots(slots, projectEdit);
                }
                case 8 -> {
                    System.out.print("Toggle visibility (true/false): ");
                    boolean visible = sc.nextBoolean();
                    edited = BTOProjectController.editVisibility(visible, projectEdit);
                }
                case 9 -> {
                    System.out.println("Exiting edit project menu.");
                    running = false;
                }
                default -> {
                    System.out.println("Invalid option.");
                }
            }
            if (edited) {
                System.out.println("Project updated!");
                System.out.println(projectEdit.toString());
            } else {
                System.out.println("Failed to update project");
            }
        }
    }

    /**
     * Delete a project managed by the manager
     * @param manager Manager deleting the project
     * @param sc Scanner object for user input
     */
    private void deleteProject(Manager manager, Scanner sc) {
        if (manager.getManagedProjects().isEmpty()) {
            System.out.println("No projects to delete.");
            return;
        }
        System.out.print("Delete project: ");
        String projectName = sc.nextLine();
        boolean deleted = BTOProjectController.deleteProject(manager, projectName);
        if (deleted) {
            System.out.printf("Project %s successfully deleted!\n", projectName);
        } else {
            System.out.println("Project not found");
        }
        
    }
}
