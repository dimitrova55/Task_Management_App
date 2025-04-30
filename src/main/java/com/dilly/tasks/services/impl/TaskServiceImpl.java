package com.dilly.tasks.services.impl;

import com.dilly.tasks.domain.entities.Task;
import com.dilly.tasks.domain.entities.TaskList;
import com.dilly.tasks.domain.entities.TaskPriority;
import com.dilly.tasks.domain.entities.TaskStatus;
import com.dilly.tasks.repositories.TaskListRepository;
import com.dilly.tasks.repositories.TaskRepository;
import com.dilly.tasks.services.TaskService;
import jakarta.persistence.EntityNotFoundException;
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

        if(task.getId() != null){
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
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        newTask.setDueDate(task.getDueDate());
        newTask.setPriority(priority);
        newTask.setStatus(TaskStatus.OPEN);
        newTask.setTaskList(taskList);

        return taskRepository.save(newTask);

    }

    @Override
    public Task getTask(UUID taskListId, UUID taskId) {
        Optional<Task> task = taskRepository.findByTaskListIdAndId(taskListId, taskId);
        return task.orElseThrow(
                () -> new EntityNotFoundException("Task with id: " + taskId + " cannot be found."));
    }

    @Override
    @Transactional
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {

        if(task.getId() == null)
        {
            throw new IllegalArgumentException("Task must have ID.");
        }

        if(! taskId.equals(task.getId())){
            throw new IllegalArgumentException("Task IDs do not match.");
        }

        if(task.getPriority() == null){
            throw new IllegalArgumentException("Task must have valid priority.");
        }

        if(task.getStatus() == null){
            throw new IllegalArgumentException("Task must have valid status.");
        }

        Task savedTask = taskRepository.findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task cannot be found."));

        savedTask.setTitle(task.getTitle());
        savedTask.setDescription(task.getDescription());
        savedTask.setDueDate(task.getDueDate());
        savedTask.setPriority(task.getPriority());
        savedTask.setStatus(task.getStatus());
        savedTask.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(savedTask);
    }

    @Override
    @Transactional
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
}