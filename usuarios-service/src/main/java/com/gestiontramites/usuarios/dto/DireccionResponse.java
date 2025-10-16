package com.gestiontramites.usuarios.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DireccionResponse {

    private Long id;
    private String calle;
    private String numero;
    private String piso;
    private String puerta;
    private String codigoPostal;
    private String ciudad;
    private String provincia;
    private String pais;

    // Método helper para dirección completa
    public String getDireccionCompleta() {
        StringBuilder sb = new StringBuilder();
        sb.append(calle).append(" ").append(numero);

        if (piso != null && !piso.isEmpty()) {
            sb.append(", ").append(piso);
        }
        if (puerta != null && !puerta.isEmpty()) {
            sb.append(" ").append(puerta);
        }

        sb.append(", ").append(codigoPostal)
                .append(" ").append(ciudad)
                .append(", ").append(provincia)
                .append(", ").append(pais);

        return sb.toString();
    }
}