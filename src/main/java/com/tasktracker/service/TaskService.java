package com.tasktracker.service;

import com.tasktracker.exception.TaskNotFound;
import com.tasktracker.model.Task;
import com.tasktracker.model.TaskStatus;

import java.util.List;

public interface TaskService {
    Task createTask(String description) throws TaskNotFound;
    Task updateTaskDescription(int id, String description) throws TaskNotFound;
    Task updateTaskStatus(int id, TaskStatus status) throws TaskNotFound;
    Task deleteTask(int id) throws TaskNotFound;
    List<Task> getAllTasks();
    List<Task> getTaskByStatus(TaskStatus status);
}
