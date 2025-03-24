package dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    @NotBlank(message = "El título no puede estar vacío")
    private String title;

    private String description;

    private LocalDateTime dueDate;

    @NotNull(message = "El identificador del estado es obligatorio")
    private Long statusId;

}
