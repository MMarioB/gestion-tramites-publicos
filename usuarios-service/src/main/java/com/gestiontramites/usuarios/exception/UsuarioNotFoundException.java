package com.gestiontramites.usuarios.exception;

public class UsuarioNotFoundException extends RuntimeException {

    public UsuarioNotFoundException(Long id) {
        super("No se encontró el usuario con ID: " + id);
    }

    public UsuarioNotFoundException(String dni) {
        super("No se encontró el usuario con DNI: " + dni);
    }
}