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

    private Task() {
        this.id = 0;
        this.description = null;
        this.status = null;
        this.createdAt = null;
        this.updatedAt = null;
    }

    private Task(Builder builder) {
        this.id = builder.id;
        this.description = builder.description;
        this.status = builder.status;
        this.createdAt = (builder.createdAt != null) ? builder.createdAt : LocalDateTime.now();
        this.updatedAt = (builder.updatedAt != null) ? builder.updatedAt : LocalDateTime.now();
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
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt);
    }

    public static Task create(String description) {
        return new Builder()
                .description(description)
                .status(TaskStatus.TODO)
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
        private TaskStatus status = TaskStatus.TODO;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder fromPrototype(Task prototype) {
            this.description = prototype.getDescription();
            this.status = prototype.getStatus();
            this.id = prototype.getId();
            this.createdAt = prototype.getCreatedAt();
            this.updatedAt = prototype.getUpdatedAt();
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

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }
}