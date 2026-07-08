package br.com.application.todolist.task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    TaskModel findByTitle(String title);

    List<TaskModel> findByUserId(UUID userId);

    Optional<TaskModel> findByIdAndUserId(UUID id, UUID userId);

    Optional<TaskModel> findByTitleAndUserId(String title, UUID userId);
}
