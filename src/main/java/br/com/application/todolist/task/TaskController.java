package br.com.application.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.application.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  private final ITaskRepository taskRepository;

  public TaskController(ITaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @PostMapping("/")
  public ResponseEntity<?> create(
      @Valid @RequestBody TaskModel task,
      HttpServletRequest request) {
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

  @GetMapping("/")
  public List<TaskModel> list(HttpServletRequest request) {
    var userId = request.getAttribute("idUser");
    var tasks = this.taskRepository.findByUserId((UUID) userId);
    return tasks;
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
      @Valid @PathVariable UUID id,
      @RequestBody TaskModel task,
      HttpServletRequest request) {

    var userId = request.getAttribute("idUser");

    if (userId == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
          "error", "Unauthorized",
          "message", "User not authenticated"));
    }

    if (task.getTitle() != null) {
      var taskWithSameTitle = this.taskRepository.findByTitleAndUserId(
          task.getTitle(),
          (UUID) userId);

      if (taskWithSameTitle.isPresent() && !taskWithSameTitle.get().getId().equals(id)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
            "error", "Bad Request",
            "message", "Task title already exists"));
      }
    }

    var taskOptional = this.taskRepository.findByIdAndUserId(id, (UUID) userId);

    if (taskOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
          "error", "Not Found",
          "message", "Task not found or does not belong to this user"));
    }

    var taskToUpdate = taskOptional.get();

    Utils.copyNonNullProperties(task, taskToUpdate, "id", "userId", "createdAt");

    var taskUpdated = this.taskRepository.save(taskToUpdate);

    return ResponseEntity.ok(Map.of(
        "message", "Task updated successfully",
        "task", taskUpdated));
  }
}