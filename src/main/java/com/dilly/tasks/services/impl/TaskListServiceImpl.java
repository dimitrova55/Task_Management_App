package com.dilly.tasks.services.impl;

import com.dilly.tasks.domain.entities.TaskList;
import com.dilly.tasks.repositories.TaskListRepository;
import com.dilly.tasks.services.TaskListService;
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
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    /* GET all task lists */
    @Override
    public List<TaskList> listTaskList() {
        List<TaskList> taskList = taskListRepository.findAll();
        return taskList;
    }

    /* POST create new task list */
    @Override
    @Transactional
    public TaskList createTaskList(TaskList taskList) {

        // check if the task already exists
        if(taskList.getId() != null)
        {
            throw new IllegalArgumentException("Task list already has an ID!");
        }
        if(null == taskList.getTitle() || taskList.getTitle().isBlank()){
            throw new IllegalArgumentException("Task list title must be present!.");
        }

        TaskList newTaskList = taskListRepository.save(taskList);
        return newTaskList;
    }

    /* GET a task list by its ID */
    @Override
    public TaskList getTaskList(UUID listId) {
        Optional<TaskList> taskList = taskListRepository.findById(listId);
        return taskList.orElseThrow(
                () -> new EntityNotFoundException("Task list with id: " + listId + " does not exist.")
        );
    }

    @Override
    @Transactional
    public TaskList updateTaskList(UUID listId, TaskList taskList) {
        if(taskList.getId() == null){
            throw new IllegalArgumentException("Task list must have an ID.");
        }

        // check if the 2 IDs are the same
        if(! listId.equals(taskList.getId())){
            throw new IllegalArgumentException("Changing task list ID is not permitted.");
        }

        TaskList existingTaskList = taskListRepository.findById(listId)
                .orElseThrow(() -> new IllegalStateException("Task list not found."));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdatedAt(LocalDateTime.now());

        return taskListRepository.save(existingTaskList);
    }

    /* DELETE an existing task list */
    @Override
    public void deleteTaskList(UUID listId) {
        taskListRepository.deleteById(listId);
    }

}
