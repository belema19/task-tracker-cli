package com.tasktracker.repository;

import com.tasktracker.exception.TaskNotFound;
import com.tasktracker.model.Task;
import com.tasktracker.model.TaskStatus;

import java.util.*;

public class InMemoryTaskRepository implements TaskRepository {
    private Map<Integer, Task> tasks = new HashMap<>();

    private int nextId = 1;

    @Override
    public Task save(Task task) throws TaskNotFound {
       int taskId = task.getId();

       if (taskId == 0) {
       Task newTask = task.toBuilder()
                          .id(nextId)
                          .build();

       tasks.put(nextId, newTask);
       nextId++;
       return newTask;
       }

       if (tasks.replace(taskId, task) == null) {
           throw new TaskNotFound("update", taskId);
       }
       return task;
    }

    @Override
    public Optional<Task> findById(int id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Task> findByStatus(TaskStatus status) {
        return tasks.values().stream()
                        .filter(task -> task.getStatus() == status)
                        .toList();
    }

    @Override
    public void delete(int id) throws TaskNotFound {
        if (!tasks.containsKey(id)) {
            throw new TaskNotFound("delete", id);
        }
        tasks.remove(id);
    }

}
