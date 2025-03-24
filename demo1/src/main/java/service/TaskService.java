package service;

import dto.TaskRequest;
import dto.TaskResponse;
import lombok.RequiredArgsConstructor;
import model.Task;
import model.TaskStatus;
import model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.TaskRepository;
import repository.TaskStatusRepository;
import repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskStatusRepository taskStatusRepository;

    public List<TaskResponse> getAllTasksForCurrentUser() {
        User currentUser = getCurrentUser();
        return taskRepository.findByUser(currentUser).stream()
                .map(TaskResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public TaskResponse getTaskById(Long id) {
        User currentUser = getCurrentUser();
        Task task = taskRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con id: " + id));

        return TaskResponse.fromEntity(task);
    }

    @Transactional
    public TaskResponse createTask(TaskRequest taskRequest) {
        User currentUser = getCurrentUser();
        TaskStatus status = getTaskStatus(taskRequest.getStatusId());

        Task task = Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .dueDate(taskRequest.getDueDate())
                .status(status)
                .user(currentUser)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Task savedTask = taskRepository.save(task);
        return TaskResponse.fromEntity(savedTask);
    }

    @Transactional
    public TaskResponse updateTask(Long id, TaskRequest taskRequest) {
        User currentUser = getCurrentUser();
        Task existingTask = taskRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con id: " + id));

        TaskStatus status = getTaskStatus(taskRequest.getStatusId());

        existingTask.setTitle(taskRequest.getTitle());
        existingTask.setDescription(taskRequest.getDescription());
        existingTask.setDueDate(taskRequest.getDueDate());
        existingTask.setStatus(status);
        existingTask.setUpdatedAt(LocalDateTime.now());

        Task updatedTask = taskRepository.save(existingTask);
        return TaskResponse.fromEntity(updatedTask);
    }

    @Transactional
    public void deleteTask(Long id) {
        User currentUser = getCurrentUser();
        Task task = taskRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con id: " + id));

        taskRepository.delete(task);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("Usuario autenticado no encontrado en la base de datos"));
    }

    private TaskStatus getTaskStatus(Long statusId) {
        return taskStatusRepository.findById(statusId)
                .orElseThrow(() -> new ResourceNotFoundException("Estado de tarea no encontrado con id: " + statusId));
    }
}
