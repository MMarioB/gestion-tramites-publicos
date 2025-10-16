package com.gestiontramites.tramites.service;

import com.gestiontramites.tramites.dto.TramiteRequest;
import com.gestiontramites.tramites.client.UsuariosServiceClient;
import com.gestiontramites.tramites.dto.TramiteResponse;
import com.gestiontramites.tramites.model.EstadoTramite;
import com.gestiontramites.tramites.model.Tramite;
import com.gestiontramites.tramites.repository.TramiteRepository;
import com.gestiontramites.tramites.exception.TramiteNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TramiteService {
    private final TramiteRepository tramiteRepository;
    private final UsuariosServiceClient usuariosServiceClient;

    /**
     * Crear un nuevo trámite
     */
    @Transactional
    public TramiteResponse crearTramite(TramiteRequest request) {
        log.info("Creando nuevo trámite con título: {}", request.getTitulo());

        if (!usuariosServiceClient.existeUsuario(request.getSolicitanteId())) {
            throw new RuntimeException("El usuario con ID " + request.getSolicitanteId() + " no existe");
        }

        Tramite tramite = Tramite.builder()
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .solicitanteId(request.getSolicitanteId())
                .estado(EstadoTramite.BORRADOR)
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .creadoPor(request.getSolicitanteId())
                .build();

        // Guardar en MongoDB
        Tramite tramiteGuardado = tramiteRepository.save(tramite);

        log.info("Trámite creado exitosamente con ID: {}", tramiteGuardado.getId());

        // Convertir la entidad a DTO de respuesta
        return mapToResponse(tramiteGuardado);
    }

    /**
     * Obtener todos los trámites
     */
    public List<TramiteResponse> obtenerTodos() {
        log.info("Obteniendo todos los trámites");

        return tramiteRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtener un trámite por ID
     */
    public TramiteResponse obtenerPorId(String id) {
        log.info("Buscando trámite con ID: {}", id);

        Tramite tramite = tramiteRepository.findById(id)
                .orElseThrow(() -> new TramiteNotFoundException(id));

        return mapToResponse(tramite);
    }

    /**
     * Obtener trámites por estado
     */
    public List<TramiteResponse> obtenerPorEstado(EstadoTramite estado) {
        log.info("Buscando trámites con estado: {}", estado);

        return tramiteRepository.findByEstado(estado)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtener trámites de un solicitante
     */
    public List<TramiteResponse> obtenerPorSolicitante(String solicitanteId) {
        log.info("Buscando trámites del solicitante: {}", solicitanteId);

        return tramiteRepository.findBySolicitanteId(solicitanteId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Actualizar un trámite
     */
    @Transactional
    public TramiteResponse actualizarTramite(String id, TramiteRequest request) {
        log.info("Actualizando trámite con ID: {}", id);

        Tramite tramite = tramiteRepository.findById(id)
                .orElseThrow(() -> new TramiteNotFoundException(id));

        // Actualizar campos
        tramite.setTitulo(request.getTitulo());
        tramite.setDescripcion(request.getDescripcion());
        tramite.setFechaActualizacion(LocalDateTime.now());
        tramite.setModificadoPor(request.getSolicitanteId());

        Tramite tramiteActualizado = tramiteRepository.save(tramite);

        log.info("Trámite actualizado exitosamente");

        return mapToResponse(tramiteActualizado);
    }

    /**
     * Cambiar estado de un trámite
     */
    @Transactional
    public TramiteResponse cambiarEstado(String id, EstadoTramite nuevoEstado) {
        log.info("Cambiando estado del trámite {} a {}", id, nuevoEstado);

        Tramite tramite = tramiteRepository.findById(id)
                .orElseThrow(() -> new TramiteNotFoundException(id));

        tramite.setEstado(nuevoEstado);
        tramite.setFechaActualizacion(LocalDateTime.now());

        Tramite tramiteActualizado = tramiteRepository.save(tramite);

        log.info("Estado cambiado exitosamente");

        // TODO: Aquí más adelante emitiremos un evento Kafka

        return mapToResponse(tramiteActualizado);
    }

    /**
     * Eliminar un trámite
     */
    @Transactional
    public void eliminarTramite(String id) {
        log.info("Eliminando trámite con ID: {}", id);

        if (!tramiteRepository.existsById(id)) {
            throw new TramiteNotFoundException(id);
        }

        tramiteRepository.deleteById(id);

        log.info("Trámite eliminado exitosamente");
    }

    /**
     * Método privado para convertir Tramite a TramiteResponse
     */
    private TramiteResponse mapToResponse(Tramite tramite) {
        return TramiteResponse.builder()
                .id(tramite.getId())
                .titulo(tramite.getTitulo())
                .descripcion(tramite.getDescripcion())
                .solicitanteId(tramite.getSolicitanteId())
                .estado(tramite.getEstado())
                .fechaCreacion(tramite.getFechaCreacion())
                .fechaActualizacion(tramite.getFechaActualizacion())
                .build();
    }
}