package com.gestiontramites.tramites.controller;

import com.gestiontramites.tramites.dto.TramiteRequest;
import com.gestiontramites.tramites.dto.TramiteResponse;
import com.gestiontramites.tramites.model.EstadoTramite;
import com.gestiontramites.tramites.service.TramiteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tramites")
@RequiredArgsConstructor
@Slf4j
public class TramiteController {

    private final TramiteService tramiteService;

    /**
     * POST /api/tramites
     * Crear un nuevo trámite
     */
    @PostMapping
    public ResponseEntity<TramiteResponse> crearTramite(
            @Valid @RequestBody TramiteRequest request) {
        log.info("REST request para crear trámite: {}", request.getTitulo());
        TramiteResponse response = tramiteService.crearTramite(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/tramites
     * Obtener todos los trámites
     * Opcionalmente puede filtrar por estado: /api/tramites?estado=ENVIADO
     */
    @GetMapping
    public ResponseEntity<List<TramiteResponse>> obtenerTramites(
            @RequestParam(required = false) EstadoTramite estado) {
        log.info("REST request para obtener trámites. Filtro estado: {}", estado);
        List<TramiteResponse> tramites;

        if (estado != null) {
            tramites = tramiteService.obtenerPorEstado(estado);
        } else {
            tramites = tramiteService.obtenerTodos();
        }

        return ResponseEntity.ok(tramites);
    }

    /**
     * GET /api/tramites/{id}
     * Obtener un trámite por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TramiteResponse> obtenerTramitePorId(
            @PathVariable String id) {
        log.info("REST request para obtener trámite con ID: {}", id);
        TramiteResponse tramite = tramiteService.obtenerPorId(id);

        return ResponseEntity.ok(tramite);
    }

    /**
     * GET /api/tramites/solicitante/{solicitanteId}
     * Obtener trámites de un solicitante específico
     */
    @GetMapping("/solicitante/{solicitanteId}")
    public ResponseEntity<List<TramiteResponse>> obtenerTramitesPorSolicitante(
            @PathVariable String solicitanteId) {
        log.info("REST request para obtener trámites del solicitante: {}", solicitanteId);
        List<TramiteResponse> tramites = tramiteService.obtenerPorSolicitante(solicitanteId);

        return ResponseEntity.ok(tramites);
    }

    /**
     * PUT /api/tramites/{id}
     * Actualizar un trámite completo
     */
    @PutMapping("/{id}")
    public ResponseEntity<TramiteResponse> actualizarTramite(
            @PathVariable String id,
            @Valid @RequestBody TramiteRequest request) {
        log.info("REST request para actualizar trámite con ID: {}", id);
        TramiteResponse tramite = tramiteService.actualizarTramite(id, request);

        return ResponseEntity.ok(tramite);
    }

    /**
     * PATCH /api/tramites/{id}/estado
     * Cambiar solo el estado de un trámite
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<TramiteResponse> cambiarEstado(
            @PathVariable String id,
            @RequestParam EstadoTramite nuevoEstado) {
        log.info("REST request para cambiar estado del trámite {} a {}", id, nuevoEstado);
        TramiteResponse tramite = tramiteService.cambiarEstado(id, nuevoEstado);

        return ResponseEntity.ok(tramite);
    }

    /**
     * DELETE /api/tramites/{id}
     * Eliminar un trámite
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTramite(@PathVariable String id) {
        log.info("REST request para eliminar trámite con ID: {}", id);
        tramiteService.eliminarTramite(id);

        return ResponseEntity.noContent().build();
    }
}