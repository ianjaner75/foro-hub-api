package com.alura.forohub.controller;

import com.alura.forohub.domain.curso.Curso;
import com.alura.forohub.domain.curso.CursoRepository;
import com.alura.forohub.domain.topico.Topico;
import com.alura.forohub.domain.topico.TopicoRepository;
import com.alura.forohub.domain.topico.dto.*;
import com.alura.forohub.domain.usuario.Usuario;
import com.alura.forohub.domain.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoRepository topicoRepo;
    private final UsuarioRepository usuarioRepo;
    private final CursoRepository cursoRepo;

    public TopicoController(TopicoRepository t, UsuarioRepository u, CursoRepository c) {
        this.topicoRepo = t; this.usuarioRepo = u; this.cursoRepo = c;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetalleTopico> crear(@RequestBody @Valid DatosRegistroTopico datos) {
        var titulo = datos.titulo().trim();
        var mensaje = datos.mensaje().trim();

        // TÍTULO duplicado
        if (topicoRepo.existsByTitulo(titulo)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Título ya existe");
        }

        // MENSAJE duplicado
        if (topicoRepo.existsByMensaje(mensaje)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Mensaje ya existe");
        }

        // Autor / curso (igual que ya tienes)
        Usuario autor = usuarioRepo.findById(datos.autorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado"));
        Curso curso = cursoRepo.findById(datos.cursoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));

        Topico topico = new Topico(null, titulo, mensaje, null, null, autor, curso);
        topico = topicoRepo.save(topico);

        var body = new DatosDetalleTopico(
                topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getFechaCreacion(), topico.getStatus(),
                topico.getAutor().getId(), topico.getCurso().getId()
        );
        return ResponseEntity.created(URI.create("/topicos/" + topico.getId())).body(body);
    }


    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listar(
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC)
            Pageable pageable) {
        var page = topicoRepo.findAll(pageable)
                .map(t -> new DatosListadoTopico(
                        t.getId(), t.getTitulo(), t.getMensaje(),
                        t.getFechaCreacion(), t.getStatus(),
                        t.getAutor().getId(), t.getCurso().getId()));
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> detalle(@PathVariable Long id) {
        var t = topicoRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));
        var dto = new DatosDetalleTopico(
                t.getId(), t.getTitulo(), t.getMensaje(),
                t.getFechaCreacion(), t.getStatus(),
                t.getAutor().getId(), t.getCurso().getId()
        );
        return ResponseEntity.ok(dto);
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosDetalleTopico> actualizar(@PathVariable Long id,
                                                         @RequestBody @Valid DatosActualizacionTopico datos) {
        var t = topicoRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));

        String nuevoTitulo  = datos.titulo()  != null ? datos.titulo().trim()  : null;
        String nuevoMensaje = datos.mensaje() != null ? datos.mensaje().trim() : null;

        // Si viene TÍTULO y realmente cambia -> valida duplicado por campo
        if (nuevoTitulo != null && !nuevoTitulo.isBlank()
                && !nuevoTitulo.equals(t.getTitulo())) {
            if (topicoRepo.existsByTituloAndIdNot(nuevoTitulo, id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Título ya existe");
            }
        }

        // Si viene MENSAJE y realmente cambia -> valida duplicado por campo
        if (nuevoMensaje != null && !nuevoMensaje.isBlank()
                && !nuevoMensaje.equals(t.getMensaje())) {
            if (topicoRepo.existsByMensajeAndIdNot(nuevoMensaje, id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Mensaje ya existe");
            }
        }

        // Curso (si cambia)
        Curso nuevoCurso = (datos.cursoId() != null)
                ? cursoRepo.findById(datos.cursoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"))
                : null;


        t.actualizar(nuevoTitulo, nuevoMensaje, datos.status(), nuevoCurso);

        var dto = new DatosDetalleTopico(
                t.getId(), t.getTitulo(), t.getMensaje(),
                t.getFechaCreacion(), t.getStatus(),
                t.getAutor().getId(), t.getCurso().getId()
        );
        return ResponseEntity.ok(dto);
    }



    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!topicoRepo.existsById(id)) {
            // <- devuelve 404 con cuerpo {"status":404,"error":"Tópico no encontrado"} si tienes el GlobalExceptionHandler
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado");
        }
        topicoRepo.deleteById(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
