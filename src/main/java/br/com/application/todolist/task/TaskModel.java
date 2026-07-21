package br.com.application.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private UUID userId;

    @NotBlank(message = "Title is required")
    @Size(max = 50, message = "The title field must contain a maximum of 50 characters")
    @Column(unique = true, nullable = false)
    private String title;
    private String description;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private String priority;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
