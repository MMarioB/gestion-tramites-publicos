package com.gestiontramites.tramites.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TramiteRequest {
    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotBlank(message = "El ID del solicitante es obligatorio")
    private String solicitanteId;
}