package com.alura.forohub.controller;

import com.alura.forohub.infra.security.TokenService;
import com.alura.forohub.infra.security.dto.DatosAutenticacion;
import com.alura.forohub.infra.security.dto.DatosJWTToken;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<DatosJWTToken> login(@RequestBody @Valid DatosAutenticacion datos) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(datos.email(), datos.contrasena())
        );

        // El principal aquí es un org.springframework.security.core.userdetails.User
        String email = auth.getName(); // = username
        String jwt = tokenService.generarTokenEmail(email);

        return ResponseEntity.ok(new DatosJWTToken(jwt));
    }
}
