package com.dilly.tasks.services;

import com.dilly.tasks.domain.entities.TaskList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskListService {

    List<TaskList> listTaskList();

    TaskList createTaskList(TaskList taskList);

    TaskList getTaskList(UUID listId);

    TaskList updateTaskList(UUID listId, TaskList taskList);

    void deleteTaskList(UUID listId);
}
