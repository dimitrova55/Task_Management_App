package com.dilly.tasks.services;

import com.dilly.tasks.domain.entities.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    List<Task> listTasks(UUID taskListId);

    Task createTask(UUID taskListId, Task task);

    // get task by id
    Task getTask(UUID taskListId, UUID taskId);

    // update task
    Task updateTask(UUID taskListId, UUID taskId, Task task);

    // delete task
    void deleteTask(UUID taskListId, UUID taskId);
}
