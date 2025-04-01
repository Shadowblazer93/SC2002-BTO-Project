package boundary;

import controller.BTOProjectController;
import entity.project.BTOProject;
import entity.user.Manager;
import java.util.Map;
import java.util.Scanner;

public class BTOProjectMain {
    PrintProjects printer = new PrintProjects();
    BTOProjectController projectController = new BTOProjectController();
    
    public void displayMenu(Manager manager) {
        try (Scanner sc = new Scanner(System.in)) {
            int choice;
            do { 
                System.out.print("""
                    1. Create project
                    2. Edit project
                    3. Delete project
                    4. View all projects
                    5. View my projects
                    6. Exit
                    """);
                choice = sc.nextInt();
                switch (choice) {
                    case 1 -> {
                        createProject(manager);
                    }
                    case 2 -> {
                        editProject(manager);
                        break;
                    }
                    case 3 -> {
                        deleteProject(manager);
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

    private void createProject(Manager manager) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Project name: ");
            String projectName = sc.nextLine();
            if (projectController.projectExist(projectName)) {
                System.out.println("Project already exists.");
                return;
            }

            System.out.print("Project neighbourhood: ");
            String neighbourhood = sc.nextLine();
            System.out.print("""
                    Flat Type:
                    2 for Two-Room
                    3 for Three-Room
                    Enter number: 
                    """);
            int flatType = sc.nextInt();

            BTOProject createdProject = projectController.createProject(manager, projectName, neighbourhood);
            if (createdProject == null) {
                System.out.println("Could not create project.");
            } else {
                System.out.printf("Project %s successfully created!", projectName);
            }
        }
    }

    private void editProject(Manager manager) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter name of project to edit: ");
            String projectName = sc.nextLine();
            Map<Integer, String[]> options = Map.of(
                1, new String[]{"Project Name", "NAME"},
                2, new String[]{"Project neighbourhood", "NEIGHBOURHOOD"},
                3, new String[]{"Project flat type", "FLAT_TYPE"},
                4, new String[]{"Number of units", "NUM_UNITS"},
                5, new String[]{"Application opening date", "OPENING_DATE"},
                6, new String[]{"Application opening date", "CLOSING_DATE"}
            );
            System.out.println("What would you like to edit?");
            options.forEach((key, value) -> System.out.println(key + ". " + value[0]));

            System.out.print("(Enter number) Attribute to edit: ");
            int choice = sc.nextInt();
            sc.nextLine();

            String attribute = options.get(choice)[1];
            if (attribute == null) {
                System.out.println("Invalid choice");
            }
            System.out.print("Enter new value: ");
            String value = sc.nextLine();

            boolean edited = projectController.editProject(manager, projectName, attribute, value);
            if (edited) {
                System.out.println("Project updated!");
                // print update project details ?
            } else {
                System.out.println("Failed to update project");
            }
        }
    }

    private void deleteProject(Manager manager) {
        try (Scanner sc = new Scanner(System.in)) {
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
}
