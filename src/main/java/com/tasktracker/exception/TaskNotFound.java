package com.tasktracker.exception;

public class TaskNotFound extends Exception {
    public TaskNotFound(int id) {
        super(String.format("Task with ID %d could not be found in the system.", id));
    }

    public TaskNotFound(String action, int id) {
        super(String.format("Cannon perform '%s': Task with ID %d does not exist.", action, id));
    }
}
