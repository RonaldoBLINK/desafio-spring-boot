package controller;

import dto.LoginRequest;
import dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API para la autenticación de usuarios")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(
            summary = "Autenticar usuario",
            description = "Permite a los usuarios obtener un token JWT para acceder a los recursos protegidos",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Autenticación exitosa",
                            content = @Content(schema = @Schema(implementation = LoginResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Credenciales no válidas"
                    )
            }
    )
    public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}
