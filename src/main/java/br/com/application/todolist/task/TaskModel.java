package br.com.application.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

    @Column(unique = true, nullable = false, length = 100)
    private String title;
    private String description;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private String priority;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
