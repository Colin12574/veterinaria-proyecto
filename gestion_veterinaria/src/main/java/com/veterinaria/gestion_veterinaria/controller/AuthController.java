package com.veterinaria.gestion_veterinaria.controller;

import com.veterinaria.gestion_veterinaria.dto.LoginRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.LoginResponseDTO;
import com.veterinaria.gestion_veterinaria.model.Adoptante;
import com.veterinaria.gestion_veterinaria.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints de login y registro")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión con correo")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/registro")
    @Operation(summary = "Registrar un nuevo cliente")
    public ResponseEntity<LoginResponseDTO> registrar(
            @Valid @RequestBody Adoptante adoptante) {
        LoginResponseDTO creado = authService.registrar(adoptante);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }
}