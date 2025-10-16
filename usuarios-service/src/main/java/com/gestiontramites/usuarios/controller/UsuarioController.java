package com.gestiontramites.usuarios.controller;

import com.gestiontramites.usuarios.dto.UsuarioRequest;
import com.gestiontramites.usuarios.dto.UsuarioResponse;
import com.gestiontramites.usuarios.model.TipoUsuario;
import com.gestiontramites.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Slf4j
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * POST /api/usuarios
     * Crear un nuevo usuario
     */
    @PostMapping
    public ResponseEntity<UsuarioResponse> crearUsuario(
            @Valid @RequestBody UsuarioRequest request) {

        log.info("REST request para crear usuario: {} {}", request.getNombre(), request.getApellidos());

        UsuarioResponse response = usuarioService.crearUsuario(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/usuarios
     * Obtener todos los usuarios
     * Opcionalmente filtrar por tipo: /api/usuarios?tipo=CIUDADANO
     * O por activo: /api/usuarios?activo=true
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> obtenerUsuarios(
            @RequestParam(required = false) TipoUsuario tipo,
            @RequestParam(required = false) Boolean activo) {

        log.info("REST request para obtener usuarios. Filtros - tipo: {}, activo: {}", tipo, activo);

        List<UsuarioResponse> usuarios;

        if (tipo != null) {
            usuarios = usuarioService.obtenerPorTipo(tipo);
        } else if (activo != null && activo) {
            usuarios = usuarioService.obtenerActivos();
        } else {
            usuarios = usuarioService.obtenerTodos();
        }

        return ResponseEntity.ok(usuarios);
    }

    /**
     * GET /api/usuarios/{id}
     * Obtener un usuario por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerUsuarioPorId(
            @PathVariable Long id) {

        log.info("REST request para obtener usuario con ID: {}", id);

        UsuarioResponse usuario = usuarioService.obtenerPorId(id);

        return ResponseEntity.ok(usuario);
    }

    /**
     * GET /api/usuarios/dni/{dni}
     * Obtener un usuario por su DNI
     */
    @GetMapping("/dni/{dni}")
    public ResponseEntity<UsuarioResponse> obtenerUsuarioPorDni(
            @PathVariable String dni) {

        log.info("REST request para obtener usuario con DNI: {}", dni);

        UsuarioResponse usuario = usuarioService.obtenerPorDni(dni);

        return ResponseEntity.ok(usuario);
    }

    /**
     * GET /api/usuarios/buscar?q=Mario
     * Buscar usuarios por nombre o apellidos
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioResponse>> buscarUsuarios(
            @RequestParam("q") String busqueda) {

        log.info("REST request para buscar usuarios con término: {}", busqueda);

        List<UsuarioResponse> usuarios = usuarioService.buscarUsuarios(busqueda);

        return ResponseEntity.ok(usuarios);
    }

    /**
     * PUT /api/usuarios/{id}
     * Actualizar un usuario completo
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequest request) {

        log.info("REST request para actualizar usuario con ID: {}", id);

        UsuarioResponse usuario = usuarioService.actualizarUsuario(id, request);

        return ResponseEntity.ok(usuario);
    }

    /**
     * PATCH /api/usuarios/{id}/activar
     * Activar un usuario
     */
    @PatchMapping("/{id}/activar")
    public ResponseEntity<UsuarioResponse> activarUsuario(@PathVariable Long id) {

        log.info("REST request para activar usuario con ID: {}", id);

        UsuarioResponse usuario = usuarioService.cambiarEstadoActivo(id, true);

        return ResponseEntity.ok(usuario);
    }

    /**
     * PATCH /api/usuarios/{id}/desactivar
     * Desactivar un usuario (soft delete)
     */
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<UsuarioResponse> desactivarUsuario(@PathVariable Long id) {

        log.info("REST request para desactivar usuario con ID: {}", id);

        UsuarioResponse usuario = usuarioService.cambiarEstadoActivo(id, false);

        return ResponseEntity.ok(usuario);
    }

    /**
     * DELETE /api/usuarios/{id}
     * Eliminar un usuario permanentemente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {

        log.info("REST request para eliminar usuario con ID: {}", id);

        usuarioService.eliminarUsuario(id);

        return ResponseEntity.noContent().build();
    }
}