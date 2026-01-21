package com.tasktracker.service;

import com.tasktracker.exception.TaskNotFound;
import com.tasktracker.model.Task;
import com.tasktracker.model.TaskStatus;
import com.tasktracker.repository.TaskRepository;

import java.util.List;

public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task createTask(String description) {
        Task newTask = Task.create(description);
        return repository.save(newTask);
    }

    @Override
    public Task updateTaskDescription(int id, String description) throws TaskNotFound {
        Task updatedTask = repository.findById(id)
                .map(task -> task.toBuilder().description(description).build())
                .orElseThrow(() -> new TaskNotFound("update", id));

        return repository.save(updatedTask);
    }

    @Override
    public Task updateTaskStatus(int id, TaskStatus status) throws TaskNotFound {
       Task updatedTask = repository.findById(id)
               .map(task -> task.toBuilder().status(status).build())
               .orElseThrow(() -> new TaskNotFound("update", id));

       return repository.save(updatedTask);
    }

    @Override
    public Task deleteTask(int id) throws TaskNotFound {
        return repository.delete(id)
                .orElseThrow(() -> new TaskNotFound("delete", id));
    }

    @Override
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    @Override
    public List<Task> getTaskByStatus(TaskStatus status) {
        return repository.findByStatus(status);
    }
}
