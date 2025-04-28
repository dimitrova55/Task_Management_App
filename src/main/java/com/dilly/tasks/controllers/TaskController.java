package com.dilly.tasks.controllers;

import com.dilly.tasks.domain.dto.TaskDto;
import com.dilly.tasks.domain.entities.Task;
import com.dilly.tasks.mappers.TaskMapper;
import com.dilly.tasks.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task-lists/{task_list_id}/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    /* GET all tasks in a list */
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(
            @PathVariable("task_list_id") UUID taskListId)
    {
        List<Task> tasks = taskService.listTasks(taskListId);
        List<TaskDto> taskDtos = tasks.stream().map(taskMapper::toDto).toList();

        return ResponseEntity.ok(taskDtos);
    }

    /* POST create new task */
    @PostMapping
    public ResponseEntity<TaskDto> createTask(
            @PathVariable("task_list_id") UUID id, TaskDto taskDto)
    {
        Task task = taskMapper.toEntity(taskDto);
        Task createdTask = taskService.createTask(id, task);
        TaskDto createdTaskDto = taskMapper.toDto(createdTask);

        return new ResponseEntity<>(createdTaskDto,HttpStatus.CREATED);
    }
}
