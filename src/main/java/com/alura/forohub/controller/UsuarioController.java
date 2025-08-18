// com.alura.forohub.controller.UsuarioController
package com.alura.forohub.controller;

import com.alura.forohub.domain.usuario.*;
import com.alura.forohub.domain.usuario.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;

    public UsuarioController(UsuarioRepository repo, PasswordEncoder encoder) {
        this.repo = repo; this.encoder = encoder;
    }

    @PostMapping // registro abierto
    public ResponseEntity<DatosDetalleUsuario> crear(@RequestBody @Valid DatosRegistroUsuario d) {
        if (repo.findByEmail(d.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        var u = new Usuario(null, d.nombre(), d.email(), encoder.encode(d.contrasena()));
        u = repo.save(u);
        return ResponseEntity.created(URI.create("/usuarios/" + u.getId()))
                .body(new DatosDetalleUsuario(u.getId(), u.getNombre(), u.getEmail()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleUsuario> detalle(@PathVariable Long id) {
        return repo.findById(id)
                .map(u -> ResponseEntity.ok(new DatosDetalleUsuario(u.getId(), u.getNombre(), u.getEmail())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DatosDetalleUsuario> actualizar(@PathVariable Long id, @RequestBody @Valid DatosActualizarUsuario d) {
        var u = repo.findById(id).orElse(null);
        if (u == null) return ResponseEntity.notFound().build();
        if (d.nombre() != null && !d.nombre().isBlank()) u.setNombre(d.nombre().trim());
        if (d.contrasena() != null && !d.contrasena().isBlank()) u.setContrasena(encoder.encode(d.contrasena()));
        u = repo.save(u);
        return ResponseEntity.ok(new DatosDetalleUsuario(u.getId(), u.getNombre(), u.getEmail()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
