package dev.annyni.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyMap;

/**
 * todo Document type GenericRepository
 */
public interface GenericRepository<T, ID>{

    T save(T entity);

    void delete(ID id);

    T update(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();
}
