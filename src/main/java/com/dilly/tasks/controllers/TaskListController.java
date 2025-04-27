package com.dilly.tasks.controllers;

import com.dilly.tasks.domain.dto.TaskListDto;
import com.dilly.tasks.domain.entities.TaskList;
import com.dilly.tasks.mappers.TaskListMapper;
import com.dilly.tasks.services.TaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
