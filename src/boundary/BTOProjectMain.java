package boundary;

import controller.BTOProjectController;
import entity.project.BTOProject;
import entity.user.Manager;
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
            } while (choice != 3);
        }
    }

    private void createProject(Manager manager) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Project name: ");
            String projectName = sc.nextLine();

            BTOProject createdProject = projectController.createProject(manager, projectName);
            if (createdProject == null) {
                System.out.println("Could not create project");
            } else {
                System.out.printf("Project %s successfully created!", projectName);
            }
        }
    }

    private void editProject(Manager manager) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter name of project to edit: ");
            String projectName = sc.nextLine();
            System.out.println("""
                    What would you like to edit?
                    1. Project name
                    2. Project neighbourhood
                    3. Project flat type
                    """);
            int choice = sc.nextInt();
            System.out.print("Enter new value: ");
            String value = sc.nextLine();

            boolean edited = projectController.editProject(manager, projectName, choice, value);
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
