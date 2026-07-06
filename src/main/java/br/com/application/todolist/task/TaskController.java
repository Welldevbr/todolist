package br.com.application.todolist.task;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  private final ITaskRepository taskRepository;

  public TaskController(ITaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @PostMapping("/")
  public ResponseEntity<?> create(@RequestBody TaskModel task, HttpServletRequest request) {
    var taskExisting = this.taskRepository.findByTitle(task.getTitle());

    if (taskExisting != null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
          "error", "Bad Request",
          "message", "Task already exists"));
    }

    var userId = request.getAttribute("idUser");
    task.setUserId(((UUID) userId));

    var currentDate = LocalDateTime.now();
    var startedAt = task.getStartedAt();

    if (currentDate.isAfter(startedAt) || currentDate.isAfter(task.getFinishedAt())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
          "error", "Bad Request",
          "message", "The start/finishe date must be after the current date"));
    }

    if (startedAt.isAfter(task.getFinishedAt())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
          "error", "Bad Request",
          "message", "The fineshe date must be after the sart date"));
    }

    var taskCreated = this.taskRepository.save(task);
    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
        "message", "Task created successfully",
        "task", taskCreated));
  }
}
