package com.tasktracker.repository;

import com.tasktracker.exception.TaskNotFound;
import com.tasktracker.model.Task;
import com.tasktracker.model.TaskStatus;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task task) throws TaskNotFound;
    Optional<Task> findById(int id);
    List<Task> findAll();
    List<Task> findByStatus(TaskStatus status);
    Optional<Task> delete(int id) throws TaskNotFound;
}
