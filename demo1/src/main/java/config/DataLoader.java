package config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.TaskStatus;
import model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import repository.TaskStatusRepository;
import repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        log.info("Cargando datos iniciales...");

        loadTaskStatuses();

        loadUsers();

        log.info("Datos iniciales cargados correctamente.");
    }

    private void loadTaskStatuses() {
        if (taskStatusRepository.count() == 0) {
            List<TaskStatus> statuses = Arrays.asList(
                    TaskStatus.builder().name("Pendiente").description("Tarea que aún no ha sido iniciada").build(),
                    TaskStatus.builder().name("En Progreso").description("Tarea que está siendo trabajada actualmente").build(),
                    TaskStatus.builder().name("En Revisión").description("Tarea que está siendo revisada").build(),
                    TaskStatus.builder().name("Completada").description("Tarea que ha sido finalizada correctamente").build(),
                    TaskStatus.builder().name("Cancelada").description("Tarea que ha sido cancelada").build()
            );

            taskStatusRepository.saveAll(statuses);
            log.info("Estados de tareas cargados: {}", statuses.size());
        }
    }

    private void loadUsers() {
        if (userRepository.count() == 0) {
            List<User> users = Arrays.asList(
                    User.builder()
                            .username("usuario1")
                            .password(passwordEncoder.encode("password1"))
                            .email("usuario1@nuevospa.com")
                            .fullName("Usuario Uno")
                            .build(),
                    User.builder()
                            .username("usuario2")
                            .password(passwordEncoder.encode("password2"))
                            .email("usuario2@nuevospa.com")
                            .fullName("Usuario Dos")
                            .build(),
                    User.builder()
                            .username("admin")
                            .password(passwordEncoder.encode("admin123"))
                            .email("admin@nuevospa.com")
                            .fullName("Administrador")
                            .build()
            );

            userRepository.saveAll(users);
            log.info("Usuarios cargados: {}", users.size());
        }
    }


}
