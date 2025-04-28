package com.dilly.tasks.services.impl;

import com.dilly.tasks.domain.entities.Task;
import com.dilly.tasks.domain.entities.TaskList;
import com.dilly.tasks.domain.entities.TaskPriority;
import com.dilly.tasks.domain.entities.TaskStatus;
import com.dilly.tasks.repositories.TaskListRepository;
import com.dilly.tasks.repositories.TaskRepository;
import com.dilly.tasks.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Override
    @Transactional
    public Task createTask(UUID taskListId, Task task) {

        if(task.getId() == null){
            throw new IllegalArgumentException("Task already has an ID.");
        }

        if(task.getTitle() == null || task.getTitle().isBlank()){
            throw new IllegalArgumentException("Task must have a title.");
        }

        TaskPriority priority = Optional.ofNullable(task.getPriority())
                .orElse(TaskPriority.MEDIUM);

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task list ID."));

        Task newTask = new Task();
        newTask.setId(task.getId());
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        newTask.setDueDate(task.getDueDate());
        newTask.setPriority(priority);
        newTask.setStatus(TaskStatus.OPEN);
        newTask.setTaskList(taskList);

        return newTask;

    }
}