package com.gestiontramites.tramites.dto;

import com.gestiontramites.tramites.model.EstadoTramite;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TramiteResponse {

    private String id;
    private String titulo;
    private String descripcion;
    private String solicitanteId;
    private EstadoTramite estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}