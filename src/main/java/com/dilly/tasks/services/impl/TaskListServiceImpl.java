package com.dilly.tasks.services.impl;

import com.dilly.tasks.domain.entities.TaskList;
import com.dilly.tasks.repositories.TaskListRepository;
import com.dilly.tasks.services.TaskListService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


}
