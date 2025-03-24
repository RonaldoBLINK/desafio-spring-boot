package controller;

import dto.TaskRequest;
import dto.TaskResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "API para la gestión de tareas")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    @Operation(
            summary = "Listar todas las tareas",
            description = "Obtiene una lista de todas las tareas del usuario autenticado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de tareas recuperada con éxito"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "No autorizado"
                    )
            }
    )
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasksForCurrentUser());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener una tarea por ID",
            description = "Obtiene una tarea específica por su ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tarea recuperada con éxito",
                            content = @Content(schema = @Schema(implementation = TaskResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "No autorizado"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Tarea no encontrada"
                    )
            }
    )
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    @Operation(
            summary = "Crear una nueva tarea",
            description = "Crea una nueva tarea para el usuario autenticado",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Tarea creada con éxito",
                            content = @Content(schema = @Schema(implementation = TaskResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos de solicitud no válidos"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "No autorizado"
                    )
            }
    )
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest taskRequest) {
        TaskResponse createdTask = taskService.createTask(taskRequest);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar una tarea existente",
            description = "Actualiza una tarea existente por su ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tarea actualizada con éxito",
                            content = @Content(schema = @Schema(implementation = TaskResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos de solicitud no válidos"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "No autorizado"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Tarea no encontrada"
                    )
            }
    )
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest taskRequest) {
        TaskResponse updatedTask = taskService.updateTask(id, taskRequest);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una tarea",
            description = "Elimina una tarea existente por su ID",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Tarea eliminada con éxito"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "No autorizado"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Tarea no encontrada"
                    )
            }
    )
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
