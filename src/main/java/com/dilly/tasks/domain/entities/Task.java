package com.dilly.tasks.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "priority", nullable = false)
    private TaskPriority priority;

    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_list_id")
    private TaskList taskList;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id)
                && Objects.equals(title, task.title)
                && Objects.equals(description, task.description)
                && Objects.equals(dueDate, task.dueDate)
                && priority == task.priority
                && status == task.status
                && Objects.equals(createdAt, task.createdAt)
                && Objects.equals(updatedAt, task.updatedAt)
                && Objects.equals(taskList, task.taskList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, title, description,
                dueDate, priority, status,
                createdAt, updatedAt, taskList);
    }


}
