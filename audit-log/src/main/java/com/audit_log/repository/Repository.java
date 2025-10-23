package com.audit_log.repository;

import com.audit_log.model.User;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {

    Optional<T> findById(Long id);

    List<T> findAll();

    void save(T t);

    void update(Long id,T t);

    void deleteById(Long id);

    List<T> findPaged(int pagina, int tamanho);

}
