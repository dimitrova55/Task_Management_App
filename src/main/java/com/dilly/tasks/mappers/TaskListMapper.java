package com.dilly.tasks.mappers;

import com.dilly.tasks.domain.dto.TaskListDto;
import com.dilly.tasks.domain.entities.Task;
import com.dilly.tasks.domain.entities.TaskList;
import com.dilly.tasks.domain.entities.TaskStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskListMapper {

    TaskList toEntity(TaskListDto taskListDto);

    @Mapping(source = "tasks", target = "count", qualifiedByName = "calculateTaskListCount")
    @Mapping(source = "tasks", target = "progress", qualifiedByName = "calculateTaskListProgress")
    @Mapping(source = "tasks", target = "tasks")
    TaskListDto toDto(TaskList taskList);

    @Named("calculateTaskListCount")
    default long calculateTaskListCount(List<Task> tasks){
        if(tasks == null){
            return 0;
        }
        return tasks.size();
    }

    @Named("calculateTaskListProgress")
    default double calculateTaskListProgress(List<Task> tasks){
        if(tasks == null || tasks.isEmpty()){
            return 0.0;
        }
        long closedTaskCount = tasks.stream()
                .filter(task -> TaskStatus.CLOSED == task.getStatus())
                .count();

        return (double) closedTaskCount / tasks.size();
    }
}
