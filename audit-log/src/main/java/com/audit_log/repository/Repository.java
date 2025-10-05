package com.audit_log.repository;

import com.audit_log.model.User;

import java.util.List;

public interface Repository {

    <T> T getById(Long id, Class<T> type );
   // User getByEmail(String email);

    <T> List<T> getAll(Class<T> type);

    <T> T update(Class<T> type);

    <T> boolean delete(Class<T> type);

    <T> T create(Class<T> type);

}
