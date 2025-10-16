package com.gestiontramites.usuarios.service;

import com.gestiontramites.usuarios.dto.*;
import com.gestiontramites.usuarios.model.Direccion;
import com.gestiontramites.usuarios.model.TipoUsuario;
import com.gestiontramites.usuarios.model.Usuario;
import com.gestiontramites.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Crear un nuevo usuario
     */
    @Transactional
    public UsuarioResponse crearUsuario(UsuarioRequest request) {
        log.info("Creando nuevo usuario con DNI: {}", request.getDni());

        // Validar que no existe ya un usuario con ese DNI
        if (usuarioRepository.existsByDni(request.getDni())) {
            throw new RuntimeException("Ya existe un usuario con DNI: " + request.getDni());
        }

        // Validar que no existe ya un usuario con ese email
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con email: " + request.getEmail());
        }

        // Construir la dirección
        Direccion direccion = Direccion.builder()
                .calle(request.getDireccion().getCalle())
                .numero(request.getDireccion().getNumero())
                .piso(request.getDireccion().getPiso())
                .puerta(request.getDireccion().getPuerta())
                .codigoPostal(request.getDireccion().getCodigoPostal())
                .ciudad(request.getDireccion().getCiudad())
                .provincia(request.getDireccion().getProvincia())
                .pais(request.getDireccion().getPais())
                .build();

        // Construir el usuario
        Usuario usuario = Usuario.builder()
                .dni(request.getDni())
                .nombre(request.getNombre())
                .apellidos(request.getApellidos())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .tipo(request.getTipo())
                .direccion(direccion)
                .activo(true)
                .build();

        // Guardar en BD
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        log.info("Usuario creado exitosamente con ID: {}", usuarioGuardado.getId());

        return mapToResponse(usuarioGuardado);
    }

    /**
     * Obtener todos los usuarios
     */
    public List<UsuarioResponse> obtenerTodos() {
        log.info("Obteniendo todos los usuarios");

        return usuarioRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtener un usuario por ID
     */
    public UsuarioResponse obtenerPorId(Long id) {
        log.info("Buscando usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        return mapToResponse(usuario);
    }

    /**
     * Obtener un usuario por DNI
     */
    public UsuarioResponse obtenerPorDni(String dni) {
        log.info("Buscando usuario con DNI: {}", dni);

        Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con DNI: " + dni));

        return mapToResponse(usuario);
    }

    /**
     * Obtener usuarios por tipo
     */
    public List<UsuarioResponse> obtenerPorTipo(TipoUsuario tipo) {
        log.info("Buscando usuarios de tipo: {}", tipo);

        return usuarioRepository.findByTipo(tipo)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtener usuarios activos
     */
    public List<UsuarioResponse> obtenerActivos() {
        log.info("Buscando usuarios activos");

        return usuarioRepository.findByActivo(true)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Buscar usuarios por nombre o apellidos
     */
    public List<UsuarioResponse> buscarUsuarios(String busqueda) {
        log.info("Buscando usuarios con término: {}", busqueda);

        return usuarioRepository.buscarPorNombreOApellidos(busqueda)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Actualizar un usuario
     */
    @Transactional
    public UsuarioResponse actualizarUsuario(Long id, UsuarioRequest request) {
        log.info("Actualizando usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // Validar DNI si cambió
        if (!usuario.getDni().equals(request.getDni())) {
            if (usuarioRepository.existsByDni(request.getDni())) {
                throw new RuntimeException("Ya existe un usuario con DNI: " + request.getDni());
            }
            usuario.setDni(request.getDni());
        }

        // Validar email si cambió
        if (!usuario.getEmail().equals(request.getEmail())) {
            if (usuarioRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Ya existe un usuario con email: " + request.getEmail());
            }
            usuario.setEmail(request.getEmail());
        }

        // Actualizar campos
        usuario.setNombre(request.getNombre());
        usuario.setApellidos(request.getApellidos());
        usuario.setTelefono(request.getTelefono());
        usuario.setTipo(request.getTipo());

        // Actualizar dirección
        Direccion direccion = usuario.getDireccion();
        direccion.setCalle(request.getDireccion().getCalle());
        direccion.setNumero(request.getDireccion().getNumero());
        direccion.setPiso(request.getDireccion().getPiso());
        direccion.setPuerta(request.getDireccion().getPuerta());
        direccion.setCodigoPostal(request.getDireccion().getCodigoPostal());
        direccion.setCiudad(request.getDireccion().getCiudad());
        direccion.setProvincia(request.getDireccion().getProvincia());
        direccion.setPais(request.getDireccion().getPais());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        log.info("Usuario actualizado exitosamente");

        return mapToResponse(usuarioActualizado);
    }

    /**
     * Activar/Desactivar usuario (soft delete)
     */
    @Transactional
    public UsuarioResponse cambiarEstadoActivo(Long id, Boolean activo) {
        log.info("Cambiando estado activo del usuario {} a {}", id, activo);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        usuario.setActivo(activo);

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        log.info("Estado actualizado exitosamente");

        return mapToResponse(usuarioActualizado);
    }

    /**
     * Eliminar usuario (hard delete)
     */
    @Transactional
    public void eliminarUsuario(Long id) {
        log.info("Eliminando usuario con ID: {}", id);

        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }

        usuarioRepository.deleteById(id);

        log.info("Usuario eliminado exitosamente");
    }

    /**
     * Mapear Usuario a UsuarioResponse
     */
    private UsuarioResponse mapToResponse(Usuario usuario) {

        DireccionResponse direccionResponse = null;
        if (usuario.getDireccion() != null) {
            direccionResponse = DireccionResponse.builder()
                    .id(usuario.getDireccion().getId())
                    .calle(usuario.getDireccion().getCalle())
                    .numero(usuario.getDireccion().getNumero())
                    .piso(usuario.getDireccion().getPiso())
                    .puerta(usuario.getDireccion().getPuerta())
                    .codigoPostal(usuario.getDireccion().getCodigoPostal())
                    .ciudad(usuario.getDireccion().getCiudad())
                    .provincia(usuario.getDireccion().getProvincia())
                    .pais(usuario.getDireccion().getPais())
                    .build();
        }

        return UsuarioResponse.builder()
                .id(usuario.getId())
                .dni(usuario.getDni())
                .nombre(usuario.getNombre())
                .apellidos(usuario.getApellidos())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .tipo(usuario.getTipo())
                .direccion(direccionResponse)
                .activo(usuario.getActivo())
                .fechaRegistro(usuario.getFechaRegistro())
                .fechaActualizacion(usuario.getFechaActualizacion())
                .build();
    }
}