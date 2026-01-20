package com.tasktracker.model;

public enum TaskStatus {
    PENDING("Pending"),
    IN_PROGRESS("In progress"),
    DONE("Done");

    private final String description;

    TaskStatus(String description) {
        this.description = description;
    }

    public String getDescription() { return this.description; }
}
