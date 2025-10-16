package com.gestiontramites.usuarios.dto;

import com.gestiontramites.usuarios.model.TipoUsuario;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UsuarioResponse {

    private Long id;
    private String dni;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private TipoUsuario tipo;
    private DireccionResponse direccion;
    private Boolean activo;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;
}