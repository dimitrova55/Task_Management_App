package com.dilly.tasks.mappers;


import com.dilly.tasks.domain.dto.TaskDto;
import com.dilly.tasks.domain.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    Task toEntity(TaskDto taskDto);

    TaskDto toDto(Task task);
}
