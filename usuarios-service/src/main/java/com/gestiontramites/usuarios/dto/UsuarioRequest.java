package com.gestiontramites.usuarios.dto;

import com.gestiontramites.usuarios.model.TipoUsuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UsuarioRequest {

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "\\d{8}[A-Za-z]", message = "El DNI debe tener 8 dígitos y una letra")
    private String dni;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "\\d{9}", message = "El teléfono debe tener 9 dígitos")
    private String telefono;

    @NotNull(message = "El tipo de usuario es obligatorio")
    private TipoUsuario tipo;

    @Valid
    @NotNull(message = "La dirección es obligatoria")
    private DireccionDTO direccion;
}