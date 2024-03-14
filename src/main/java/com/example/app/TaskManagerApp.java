package com.example.app;

import com.example.app.service.Task;
import com.example.app.service.TaskManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TaskManagerApp {
    private static Scanner scanner;
    private static TaskManager manager;
    private static Logger logger;

    private TaskManagerApp(){}

    private static void Initialize() {
        scanner = new Scanner(System.in);
        manager = new TaskManager();
        logger = LogManager.getRootLogger();
    }

    public static void main(String[] args) {
        try {
            Initialize();
            int choice = 0;
            do {
                System.out.println("1 - Create simple task\n2 - Create priority task\n" +
                        "3 - Create task with deadline\n4 - Show current task\n5 - Show all tasks\n" +
                        "6 - Do current task\n7 - Search task by name\n8 - Search task by date of creation\n" +
                        "9 - Delete task by ID\n10 - Delete task by name\n" +
                        "11 - Delete task by name and date of creation\n0 - Exit");
                System.out.print("Input your choice: ");

                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        createTask();
                        break;
                    case 2:
                        createPriorityTask();
                        break;
                    case 3:
                        createTaskWithDeadline();
                        break;
                    case 4:
                        showCurrentTask();
                        break;
                    case 5:
                        showAllTasks();
                        break;
                    case 6:
                        doTask();
                        break;
                    case 7:
                        showAllTasksByName();
                        break;
                    case 8:
                        showAllTasksByDateOfCreation();
                        break;
                    case 9:
                        deleteTaskByID();
                        break;
                    case 10:
                        deleteTaskByName();
                        break;
                    case 11:
                        deleteTaskByNameAndDate();
                        break;
                }
            } while (choice != 0);
        } finally {
            logger.info("End of processing");
            manager.destroy();
        }
    }

    private static void createTask() {
        System.out.print("Input name of task: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Input description: ");
        String description = scanner.nextLine();
        manager.addTask(new Task(name, description));
    }

    private static void createPriorityTask() {
        System.out.print("Input name of task: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Input description: ");
        String description = scanner.nextLine();
        System.out.print("Input priority: ");
        String priorityLine = null;
        try {
            priorityLine = scanner.nextLine();
            int priority = Integer.parseInt(priorityLine);
            manager.addTask(new Task(name, description, priority));
            System.out.println("\nComplete!\n");
        } catch (NumberFormatException ex) {
            logger.info("Invalid value -> " + ex.getMessage() + " -> Input: " + priorityLine);
            System.out.println("Incorrect value!");
        }
    }

    private static void createTaskWithDeadline() {
        System.out.print("Input name of task: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Input description: ");
        String description = scanner.nextLine();
        System.out.print("Input deadline(yyyy-mm-dd): ");
        String deadline = scanner.nextLine();
        System.out.print("Input priority: ");
        int priority = scanner.nextInt();
        try {
            manager.addTask(new Task(name, description, priority, deadline));
        } catch (RuntimeException ex) {
            System.out.println(ex.getCause().toString());
        }
    }

    private static void showCurrentTask() {
        Task task = manager.getCurrentTask();
        System.out.println(task == null ? "\nTask manager is empty!\n" : task);
    }

    private static void showAllTasks() {
        System.out.println("All tasks in manager: ");
        for (Task task :
                manager.getAllTasks()) {
            System.out.println(task);
        }
    }

    private static void doTask() {
        Task task = manager.doCurrentTask();
        if (task == null)
            System.out.println("\nTask manager is empty!\n");
        else
            System.out.println(task +  "Task was completed!\n" +
                    "==================================");
    }

    private static void showAllTasksByName() {
        System.out.print("Input name of task: ");
        scanner.nextLine();
        List<Task> tasks = manager.searchTasksByName(scanner.nextLine());
        if (!tasks.isEmpty()) {
            for (Task task : tasks) {
                System.out.println(task);
            }
        } else {
            System.out.println("Tasks with this name do not exist!");
        }
    }

    private static void showAllTasksByDateOfCreation() {
        System.out.print("Input date of task(yyyy-mm-dd): ");
        scanner.nextLine();
         List<Task> tasks = manager.searchTasksByDateOfCreation(scanner.nextLine());
         if (!tasks.isEmpty()) {
             for (Task task : tasks) {
                 System.out.println(task);
             }
         } else {
             System.out.println("Tasks with this date do not exist!");
         }
    }

    private static void deleteTaskByName() {
        System.out.print("Input name of task: ");
        scanner.nextLine();
        String taskName = scanner.nextLine();
        if (manager.deleteTask(taskName))
            System.out.println("\nTask(s) was/were deleted!\n");
        else {
            logger.info("Invalid name: " + taskName);
            System.out.println("\nThere aren't task with this name!\n");
        }

    }

    private static void deleteTaskByNameAndDate() {
        System.out.print("Input name of task: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Input date of creation(yyyy-mm-dd): ");
        String date = scanner.nextLine();
        if (manager.deleteTask(name, date))
            System.out.println("\nTask(s) was/were deleted!\n");
        else {
            logger.info("Invalid input -> date: " + date + "; Task name: " + name);
            System.out.println("\nThere aren't task with this name and this date!\n");
        }
    }

    private static void deleteTaskByID() {
        System.out.print("Input ID of task: ");

        if (manager.deleteTask(scanner.nextInt()))
            System.out.println("\nTask was deleted!\n");
        else
            System.out.println("\nThere isn't task with this ID!\n");
    }

}
