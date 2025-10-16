package com.gestiontramites.tramites.repository;

import com.gestiontramites.tramites.model.EstadoTramite;
import com.gestiontramites.tramites.model.Tramite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TramiteRepository extends MongoRepository<Tramite, String> {
    List<Tramite> findByEstado(EstadoTramite estado);
    List<Tramite> findBySolicitanteId(String solicitanteId);
}