package com.tasktracker;

import com.tasktracker.exception.*;
import com.tasktracker.model.Task;
import com.tasktracker.model.TaskStatus;
import com.tasktracker.repository.InMemoryTaskRepository;
import com.tasktracker.repository.JsonTaskRepository;
import com.tasktracker.repository.TaskRepository;
import com.tasktracker.service.TaskService;
import com.tasktracker.service.TaskServiceImpl;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class TaskTrackerApp {

    private final TaskService service;
    private final Map<String, TaskStatus> ALLOWED_STATUS = Map.of(
            "done", TaskStatus.DONE,
            "in-progress", TaskStatus.IN_PROGRESS,
            "todo", TaskStatus.TODO
    );

    public TaskTrackerApp(TaskService service) {
        this.service = service;
    }

    static void main(String[] args) {
        String storage = "tasks.json";
        TaskRepository repository = new JsonTaskRepository(storage);
        TaskService service = new TaskServiceImpl(repository);

        TaskTrackerApp app = new TaskTrackerApp(service);
        app.run(args);
    }

    public void run(String[] args) {

        try {
            if (args.length == 0) {
                throw new NoArgsProvided();
            }
        } catch (NoArgsProvided e) {
            System.out.println(e.getMessage());
            return;
        }

        try {
            switch (args[0]) {
                case "add" -> {
                    evaluateArgs(args, 2);
                    handleTaskCreation(args);
                }
                case "list" -> handleTaskList(args);
                case "update" -> {
                    evaluateArgs(args, 3);
                    handleTaskUpdate(args);
                }
                case "mark" -> {
                    evaluateArgs(args, 3);
                    handleTaskMark(args);
                }
                case "delete" -> {
                    evaluateArgs(args, 2);
                    handleTaskDelete(args);
                }
            }
        } catch (NotEnoughArgs | UnknownArg | UnknownStatus | TaskNotFound e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: ID must be a valid number.");
        }

    }

    private void evaluateArgs(String[] args, int expectedArgsLength) throws NotEnoughArgs, UnknownArg {
        if (args.length < expectedArgsLength) {
            throw new NotEnoughArgs(args[0]);

        } else if (args.length > expectedArgsLength) {
            String[] unknownArgs = new String[args.length - expectedArgsLength];
            System.arraycopy(args, expectedArgsLength, unknownArgs, 0, unknownArgs.length);

            throw new UnknownArg(unknownArgs);
        }
    }

    private void handleTaskCreation(String[] args) throws TaskNotFound {
        Task newTask = service.createTask(args[1]);
        printMessage("created", newTask);
    }

    private void handleTaskList(String[] args) throws UnknownStatus {

        if (args.length == 1) {
            List<Task> tasks = service.getAllTasks();
            tasks.forEach(System.out::println);
        } else {
            if (ALLOWED_STATUS.containsKey(args[1])) {
                TaskStatus status = ALLOWED_STATUS.get(args[1]);
                List<Task> tasks = service.getTaskByStatus(status);
                tasks.forEach(System.out::println);
            } else {
                throw new UnknownStatus(args[1]);
            }
        }
    }

    private void handleTaskUpdate(String[] args) throws TaskNotFound {
        Task updatedTask = service.updateTaskDescription(Integer.parseInt(args[1]), args[2]);
        printMessage("updated", updatedTask);
    }

    private void handleTaskMark(String[] args) throws TaskNotFound, UnknownStatus {
        if (ALLOWED_STATUS.containsKey(args[2])) {
            TaskStatus taskStatus = ALLOWED_STATUS.get(args[2]);
            Task markedTask = service.updateTaskStatus(Integer.parseInt(args[1]), taskStatus);

            printMessage("marked", markedTask);
        } else {
            throw new UnknownStatus(args[2]);
        }
    }

    private void handleTaskDelete(String[] args) throws TaskNotFound {
        Task deletedTask = service.deleteTask(Integer.parseInt(args[1]));
        printMessage("deleted", deletedTask);
    }

    private void printMessage(String action, Task task) {
        String message = String.format("Task %s!\n-> %s", action, task);
        System.out.println(message);
    }

}