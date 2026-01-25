package com.tasktracker.repository;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tasktracker.exception.TaskNotFound;
import com.tasktracker.model.Task;
import com.tasktracker.model.TaskStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonTaskRepository implements TaskRepository {

     // === CLASS ELEMENTS ===

    private final Path filePath;
    private final ObjectMapper mapper;

    public JsonTaskRepository(String pathString) {
        this.filePath = Path.of(pathString);
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);

        this.mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        init();
    }

    // === HELPER METHODS ===

    private int getNextId(List<Task> tasks) {
        return tasks.stream()
                .mapToInt(Task::getId)
                .max()
                .orElse(0)
                + 1;
    }

    private void init() {
        try {
            Path parentDir = filePath.getParent();

            if (parentDir != null && Files.notExists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            if (Files.notExists(filePath)) {
                Files.writeString(filePath, "[]");
            }
        } catch (IOException e) {
            throw new RuntimeException("Couldn't configure storage file", e);
        }
    }

    private List<Task> loadJson() {
        try {
            if (Files.size(filePath) == 0) return new ArrayList<>();

            return mapper.readValue(filePath.toFile(), new TypeReference<List<Task>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load storage file", e);
        }
    }

    private void saveJson(List<Task> tasks) {
        try {
            mapper.writeValue(filePath.toFile(), tasks);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't save tasks to storage file", e);
        }
    }

    // === INTERFACE METHODS ===

    @Override
    public Optional<Task> findById(int id) {
        return loadJson().stream()
                .filter(t -> t.getId() == id)
                .findFirst();
    }

    @Override
    public List<Task> findAll() {
        return loadJson();
    }

    @Override
    public List<Task> findByStatus(TaskStatus status) {
        return loadJson().stream()
                .filter(t -> t.getStatus() == status)
                .toList();
    }

    @Override
    public Task save(Task task) throws TaskNotFound {
        List<Task> tasks = loadJson();

        if (task.getId() <= 0) {
            int newId = getNextId(tasks);

            Task newTask = new Task.Builder()
                    .fromPrototype(task)
                    .id(newId)
                    .build();

            tasks.add(newTask);
            saveJson(tasks);

            return newTask;
        } else {
            int index = -1;
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getId() == task.getId()) {
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                throw new TaskNotFound("update", task.getId());
            }

            tasks.set(index, task);
            saveJson(tasks);
            return task;
        }

    }

    @Override
    public Optional<Task> delete(int id) throws TaskNotFound {
        List<Task> tasks = loadJson();

        Task taskToDelete = tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new TaskNotFound("delete", id));

        tasks.remove(taskToDelete);

        saveJson(tasks);
        return Optional.of(taskToDelete);
    }

}