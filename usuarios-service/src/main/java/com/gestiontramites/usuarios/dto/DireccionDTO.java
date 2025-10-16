package com.gestiontramites.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DireccionDTO {

    @NotBlank(message = "La calle es obligatoria")
    private String calle;

    @NotBlank(message = "El número es obligatorio")
    private String numero;

    private String piso;

    private String puerta;

    @NotBlank(message = "El código postal es obligatorio")
    @Pattern(regexp = "\\d{5}", message = "El código postal debe tener 5 dígitos")
    private String codigoPostal;

    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;

    @NotBlank(message = "La provincia es obligatoria")
    private String provincia;

    @NotBlank(message = "El país es obligatorio")
    private String pais;
}