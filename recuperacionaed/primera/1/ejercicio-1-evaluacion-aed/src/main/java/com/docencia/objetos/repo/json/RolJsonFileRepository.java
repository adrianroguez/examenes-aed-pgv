package com.docencia.objetos.repo.json;

import com.docencia.objetos.domain.Rol;
import com.docencia.objetos.repo.interfaces.RolRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Implementación de repositorio para la entidad Rol utilizando un archivo JSON
 * como persistencia.
 * Utiliza Jackson para la serialización/deserialización y
 * ReentrantReadWriteLock para concurrencia.
 */
public class RolJsonFileRepository implements RolRepository {

    private final Path path;
    private final ObjectMapper mapper;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public RolJsonFileRepository(Path path, ObjectMapper mapper) {
        this.path = path;
        this.mapper = mapper;
    }

    /**
     * Obtiene todos los roles del archivo JSON.
     * Utiliza bloqueo de lectura para acceso seguro.
     * 
     * @return Lista de roles.
     */
    @Override
    public List<Rol> findAll() {
        lock.readLock().lock();
        try {
            if (!Files.exists(path)) {
                return new ArrayList<>();
            }
            Rol[] roles = mapper.readValue(path.toFile(), Rol[].class);
            return new ArrayList<>(Arrays.asList(roles));
        } catch (IOException e) {
            throw new RuntimeException("Error reading roles from JSON", e);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Busca un rol por su ID.
     * 
     * @param id Identificador del rol.
     * @return Optional con el rol si existe.
     */
    @Override
    public Optional<Rol> findById(Long id) {
        return findAll().stream()
                .filter(r -> Objects.equals(r.getId(), id))
                .findFirst();
    }

    /**
     * Busca un rol por su nombre.
     * 
     * @param nombre Nombre del rol.
     * @return Optional con el rol si existe.
     */
    @Override
    public Optional<Rol> findByNombre(String nombre) {
        return findAll().stream()
                .filter(r -> Objects.equals(r.getNombre(), nombre))
                .findFirst();
    }

    /**
     * Guarda un rol (crea o actualiza).
     * Utiliza bloqueo de escritura para asegurar consistencia.
     * Si el ID es nulo, genera uno nuevo basado en el máximo existente.
     * 
     * @param rol Rol a guardar.
     * @return Rol guardado.
     */
    @Override
    public Rol save(Rol rol) {
        lock.writeLock().lock();
        try {
            List<Rol> roles = findAll();
            if (rol.getId() == null) {
                long maxId = roles.stream()
                        .mapToLong(Rol::getId)
                        .max()
                        .orElse(0L);
                rol.setId(maxId + 1);
                roles.add(rol);
            } else {
                boolean found = false;
                for (int i = 0; i < roles.size(); i++) {
                    if (Objects.equals(roles.get(i).getId(), rol.getId())) {
                        roles.set(i, rol);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    roles.add(rol);
                }
            }
            writeRoles(roles);
            return rol;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Elimina un rol por su ID.
     * Utiliza bloqueo de escritura.
     * 
     * @param id Identificador del rol a eliminar.
     */
    @Override
    public void deleteById(Long id) {
        lock.writeLock().lock();
        try {
            List<Rol> roles = findAll();
            boolean removed = roles.removeIf(r -> Objects.equals(r.getId(), id));
            if (removed) {
                writeRoles(roles);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Cuenta el número total de roles.
     * 
     * @return Número de roles.
     */
    @Override
    public long count() {
        return findAll().size();
    }

    /**
     * Escribe la lista de roles en el archivo JSON.
     * 
     * @param roles Lista de roles a escribir.
     */
    private void writeRoles(List<Rol> roles) {
        try {
            mapper.writeValue(path.toFile(), roles);
        } catch (IOException e) {
            throw new RuntimeException("Error writing roles to JSON", e);
        }
    }

}
