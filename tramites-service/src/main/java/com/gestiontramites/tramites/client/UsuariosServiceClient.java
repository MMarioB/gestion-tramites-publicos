package com.gestiontramites.tramites.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class UsuariosServiceClient {

    private final WebClient webClient;

    public UsuariosServiceClient(@Value("${microservicios.usuarios-service.url}") String usuariosServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(usuariosServiceUrl)
                .build();
    }

    /**
     * Verifica si existe un usuario por ID
     */
    public boolean existeUsuario(String usuarioId) {
        try {
            log.info("Verificando si existe usuario con ID: {}", usuarioId);

            // Llamada a usuarios-service: GET /api/usuarios/{id}
            webClient.get()
                    .uri("/api/usuarios/{id}", usuarioId)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

            log.info("Usuario {} existe", usuarioId);
            return true;

        } catch (Exception e) {
            log.warn("Usuario {} no existe o no se pudo verificar: {}", usuarioId, e.getMessage());
            return false;
        }
    }
}