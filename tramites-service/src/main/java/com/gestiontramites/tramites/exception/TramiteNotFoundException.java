package com.gestiontramites.tramites.exception;

// Excepción personalizada cuando no se encuentra un trámite
public class TramiteNotFoundException extends RuntimeException {

    public TramiteNotFoundException(String id) {
        super("No se encontró el trámite con ID: " + id);
    }
}