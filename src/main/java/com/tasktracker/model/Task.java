package com.tasktracker.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private final int id;
    private final String description;
    private final TaskStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Task(Builder builder) {
        this.id = builder.id;
        this.description = builder.description;
        this.status = builder.status;
        this.createdAt = (builder.createdAt != null) ? builder.createdAt : LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public int getId() { return this.id; }
    public String getDescription() { return this.description; }
    public TaskStatus getStatus() { return this.status; }
    public LocalDateTime getCreatedAt() { return this.createdAt; }
    public LocalDateTime getUpdatedAt() { return this.updatedAt; }

    public Builder toBuilder() {
        return new Builder()
                .id(this.id)
                .description(this.description)
                .status(this.status)
                .createdAt(this.createdAt);
    }

    public static Task create(int id, String description) {
        return new Builder()
                .id(id)
                .description(description)
                .status(TaskStatus.PENDING)
                .build();
    }

    public static Task create(int id, String description, TaskStatus status) {
        return new Builder()
                .id(id)
                .description(description)
                .status(status)
                .build();
    }

    @Override
    public String toString() {
        return String.format(
                "id=%d, description=%s, status=%s, createdAt=%s, updatedAt=%s",
                id,
                description,
                status.getDescription(),
                createdAt.format(FORMATTER),
                updatedAt.format(FORMATTER)
        );
    }

    public static class Builder {
        private int id;
        private String description;
        private TaskStatus status = TaskStatus.PENDING;
        private LocalDateTime createdAt;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder status(TaskStatus status) {
            this.status = status;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Task build() {
            return new Task(this);
        }

    }
}
