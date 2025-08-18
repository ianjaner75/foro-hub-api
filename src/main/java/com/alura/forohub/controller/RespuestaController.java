// com.alura.forohub.controller.RespuestaController
package com.alura.forohub.controller;

import com.alura.forohub.domain.respuesta.*;
import com.alura.forohub.domain.respuesta.dto.*;
import com.alura.forohub.domain.topico.TopicoRepository;
import com.alura.forohub.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    private final RespuestaRepository repo;
    private final TopicoRepository topicoRepo;
    private final UsuarioRepository usuarioRepo;

    public RespuestaController(RespuestaRepository r, TopicoRepository t, UsuarioRepository u) {
        this.repo = r; this.topicoRepo = t; this.usuarioRepo = u;
    }

    @PostMapping
    public ResponseEntity<DatosDetalleRespuesta> crear(Authentication auth,
                                                       @RequestBody @Valid DatosRegistroRespuesta d) {
        // email desde el token (SecurityFilter)
        String email = auth.getName();
        var autor = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no encontrado"));
        var topico = topicoRepo.findById(d.topicoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));

        var r = new Respuesta(null, d.mensaje().trim(), null, autor, topico);
        r = repo.save(r);

        var body = new DatosDetalleRespuesta(r.getId(), r.getMensaje(), r.getFechaCreacion(),
                r.getAutor().getId(), r.getTopico().getId());
        return ResponseEntity.created(URI.create("/respuestas/" + r.getId())).body(body);
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleRespuesta>> listar(
            @PageableDefault(size=10, sort="fechaCreacion", direction=Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required=false) Long topicoId) {

        Page<Respuesta> page = (topicoId == null)
                ? repo.findAll(pageable)
                : repo.findByTopico_Id(topicoId, pageable);

        var dto = page.map(r -> new DatosDetalleRespuesta(r.getId(), r.getMensaje(), r.getFechaCreacion(),
                r.getAutor().getId(), r.getTopico().getId()));
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleRespuesta> detalle(@PathVariable Long id) {
        return repo.findById(id)
                .map(r -> new DatosDetalleRespuesta(r.getId(), r.getMensaje(), r.getFechaCreacion(),
                        r.getAutor().getId(), r.getTopico().getId()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
