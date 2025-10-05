package com.audit_log.repository;

import com.audit_log.model.User;

import java.util.Optional;

public interface UserRepository extends Repository{

    User getById(Long id);
    Optional<User> findByEmail(String email);
}
