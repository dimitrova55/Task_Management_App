package com.dilly.tasks.repositories;

import com.dilly.tasks.domain.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    // returns all tasks in a specific task list
    List<Task> findByTaskListId(UUID taskListId);

    // returns a specific task within a specific task list
    Optional<Task> findByTaskListIdAndId(UUID taskListId, UUID id);

    void deleteByTaskListIdAndId(UUID taskListId, UUID taskId);

}
