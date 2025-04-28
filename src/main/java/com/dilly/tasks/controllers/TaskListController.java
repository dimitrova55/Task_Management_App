package com.dilly.tasks.controllers;

import com.dilly.tasks.domain.dto.TaskListDto;
import com.dilly.tasks.domain.entities.TaskList;
import com.dilly.tasks.mappers.TaskListMapper;
import com.dilly.tasks.services.TaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-list")
@RequiredArgsConstructor
public class TaskListController {

    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;

    /* GET All task lists */
    @GetMapping
    public ResponseEntity<List<TaskListDto>> listTaskLists(){
        List<TaskList> taskLists = taskListService.listTaskList();
        List<TaskListDto> taskListDtos = taskLists.stream()
                .map(taskListMapper :: toDto)
                .toList();

        return ResponseEntity.ok(taskListDtos);
    }

    /* POST create new category */
    @PostMapping
    public ResponseEntity<TaskListDto> createTaskList(@RequestBody TaskListDto taskListDto){

        TaskList taskList = taskListMapper.toEntity(taskListDto);
        TaskList createdTaskList = taskListService.createTaskList(taskList);

        return new ResponseEntity<>(
                taskListMapper.toDto(createdTaskList),
                HttpStatus.CREATED
        );
    }

    @GetMapping(path = "/{task_list_id}")
    public ResponseEntity<TaskListDto> getTaskList(@PathVariable("task_list_id") UUID id){

        TaskList taskList = taskListService.getTaskList(id);
        TaskListDto taskListDto = taskListMapper.toDto(taskList);

        return ResponseEntity.ok(taskListDto);
    }

    @PutMapping(path = "/{task_list_id}")
    public ResponseEntity<TaskListDto> updatedTaskList(
            @PathVariable("task_list_id") UUID id,
            @RequestBody TaskListDto taskListDto)
    {
        TaskList taskList = taskListMapper.toEntity(taskListDto);
        TaskList updatedTaskList = taskListService.updateTaskList(id, taskList);
        TaskListDto updatedTaskListDto = taskListMapper.toDto(updatedTaskList);

        return ResponseEntity.ok(updatedTaskListDto);
    }
}
