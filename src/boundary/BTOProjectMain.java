package boundary;

import controller.BTOProjectController;
import entity.project.BTOProject;
import entity.user.Manager;
import enums.FlatType;
import enums.UserRole;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BTOProjectMain {
    PrintProjects printer = new PrintProjects();
    BTOProjectController projectController = new BTOProjectController();
    
    public static void main(String[] args) {
        Manager manager = new Manager("123", "password", UserRole.MANAGER);
        BTOProjectMain btoProjectMain = new BTOProjectMain();
        btoProjectMain.displayMenu(manager);
    }

    public void displayMenu(Manager manager) {
        try (Scanner sc = new Scanner(System.in)) {
            int choice = 0;
            do { 
                System.out.print("""
                    ---------------------------
                      Project Management Menu
                    ---------------------------
                    1. Create project
                    2. Edit project
                    3. Delete project
                    4. View all projects
                    5. View my projects
                    6. Exit
                    """);
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
                        printer.print(manager.getManagedProjects());
                        break;
                    }
                    case 5 -> {
                        System.out.print("Exit");
                        break;
                    }
                    case 6 -> {
                        System.out.print("Exit");
                        break;
                    }
                    default -> {
                        System.out.println("Invalid option");
                    }
                }
            } while (choice != 6);
        }
    }

    private void createProject(Manager manager, Scanner sc) {
        // Project name
        System.out.print("Project name: ");
        String projectName = sc.nextLine();
        if (projectController.projectExist(projectName)) {
            System.out.println("Project already exists.");
            return;
        }
        // Project neighbourhood
        System.out.print("Project neighbourhood: ");
        String neighbourhood = sc.nextLine();
        // Types of Flat + Number of units
        Map<FlatType, Integer> unitCounts = new HashMap<>();
        for (FlatType flatType : FlatType.values()) {
            int units = 0;
            while (units <= 0) {
                try {
                    System.out.printf("Number of units for %d-Room flats: ", flatType.getNumRooms());
                    units = sc.nextInt();
                    if (units <= 0) {
                        System.out.println("Number of units must be greater than 0.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine(); 
                } 
            }
            sc.nextLine();
            unitCounts.put(flatType, units);
        }
        // Application opening date
        LocalDate oDate = null;
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
        LocalDate cDate = null;
        while (cDate == null) {
            System.out.print("Application closing date (YYYY-MM-DD): ");
            String cDateInput = sc.nextLine();
            try {
                cDate = LocalDate.parse(cDateInput);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date.");
            }
        }
        // Available HDB Office Slots
        int slots = 0;
        while (slots <= 0) {
            try {
                System.out.printf("Available HDB Office Slots: ");
                slots = sc.nextInt();
                if (slots <= 0) {
                    System.out.println("Number of slots must be greater than 0.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); 
            } 
        }

        BTOProject createdProject = projectController.createProject(manager, projectName, neighbourhood, unitCounts, oDate, cDate, slots);
        if (createdProject == null) {
            System.out.println("Could not create project.");
        } else {
            System.out.printf("Project %s successfully created!\n", projectName);
        }
    }

    private void editProject(Manager manager, Scanner sc) {
        if (manager.getManagedProjects().isEmpty()) {
            System.out.println("No projects to edit.");
            return;
        }
        System.out.print("Enter name of project to edit: ");
        String projectName = sc.nextLine();

        int choice = 0;
        while (choice != 7) {
            System.out.println("""
            What would you like to edit?
            1. Project Name
            2. Project neighbourhood
            3. Number of units
            4. Application opening date
            5. Application closing date
            6. Toggle Visibility
            7. Exit edit project menu
                """);
            System.out.print("(Enter number) Attribute to edit: ");
            choice = sc.nextInt();
            boolean edited = false;
            switch (choice) {
                case 1 -> {
                    System.out.print("New project name: ");
                    String newProjectName = sc.nextLine();
                    edited = projectController.editProjectName(manager, projectName, newProjectName);
                }
                case 2 -> {
                    System.out.print("New neighbourhood: ");
                    String newNeighbourhood = sc.nextLine();
                    edited = projectController.editNeighbourhood(manager, projectName, newNeighbourhood);
                }
                case 3 -> {
                    System.out.print("Flat type to edit (2 or 3): ");
                    int flatTypeInput = sc.nextInt();
                    FlatType flatType = FlatType.getFlatType(flatTypeInput);
                    if (flatType == null) {
                        System.out.println("Invalid flat type.");
                    }
                    System.out.print("New number of units: ");
                    int newNumUnits = sc.nextInt();
                    edited = projectController.editNumUnits(manager, projectName, flatType, newNumUnits);
                }
                case 4 -> {
                    System.out.print("New application opening date (YYYY-MM-DD): ");
                    String oDateInput = sc.nextLine();
                    LocalDate oDate = LocalDate.parse(oDateInput);
                    edited = projectController.editOpeningDate(manager, projectName, oDate);
                }
                case 5 -> {
                    System.out.print("New application closing date (YYYY-MM-DD): ");
                    String cDateInput = sc.nextLine();
                    LocalDate cDate = LocalDate.parse(cDateInput);
                    edited = projectController.editClosingDate(manager, projectName, cDate);
                }
                case 6 -> {
                    System.out.print("Toggle visibility (true/false): ");
                    boolean visible = sc.nextBoolean();
                    edited = projectController.editVisibility(manager, projectName, visible);
                }
                case 7 -> {
                    System.out.println("Exiting edit project menu.");
                }
                default -> {
                    System.out.println("Invalid option.");
                }
            }
            if (edited) {
                System.out.println("Project updated!");
                // print update project details ?
            } else {
                System.out.println("Failed to update project");
            }
        }
    }

    private void deleteProject(Manager manager, Scanner sc) {
        if (manager.getManagedProjects().isEmpty()) {
            System.out.println("No projects to delete.");
            return;
        }
        System.out.print("Delete project: ");
        String projectName = sc.nextLine();
        boolean deleted = projectController.deleteProject(manager, projectName);
        if (deleted) {
            System.out.printf("Project %s successfully deleted!\n", projectName);
        } else {
            System.out.println("Project not found");
        }
        
    }
}
