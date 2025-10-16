package com.gestiontramites.tramites.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

// Estructura estándar para respuestas de error
@Data
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
}