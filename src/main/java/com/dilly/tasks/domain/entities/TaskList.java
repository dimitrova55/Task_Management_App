package com.dilly.tasks.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "task_lists")
public class TaskList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /*
    * Deleting a task list,
    * should also delete all tasks associated with it.
    * */
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "taskList",
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Task> tasks;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TaskList taskList = (TaskList) o;
        return Objects.equals(id, taskList.id)
                && Objects.equals(title, taskList.title)
                && Objects.equals(description, taskList.description)
                && Objects.equals(createdAt, taskList.createdAt)
                && Objects.equals(updatedAt, taskList.updatedAt)
                && Objects.equals(tasks, taskList.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, createdAt, updatedAt, tasks);
    }
}
