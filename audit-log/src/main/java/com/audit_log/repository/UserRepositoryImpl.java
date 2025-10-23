package com.audit_log.repository;

import com.audit_log.model.User;
import com.audit_log.model.UserRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.util.Assert;


import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public class UserRepositoryImpl implements Repository<User> {

    private final JdbcClient jdbcClient;

    private final String SQL_FIND_USER = "select * from user where id = ?";
    private final String SQL_FIND_USER_BY_EMAIL = "select * from user where email = ?";
    private final String SQL_DELETE_USER = "delete from user where id = ?";
    private final String SQL_UPDATE_USER = "update user set nome = ?, email = ?, senha = ? where id = ?";
    private final String SQL_GET_ALL = "select * from user";
    private final String SQL_INSERT_USER = "insert into user(id, nome, email, senha) values(?,?,?,?)";
    private final String SQL_PAGER = "SELECT * FROM user ORDER BY id LIMIT ? OFFSET ?";

    public UserRepositoryImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<User> findByEmail(String email){
        return jdbcClient.sql(SQL_FIND_USER_BY_EMAIL).param(email).query(new UserRowMapper()).optional();
    }

    @Override
    public Optional<User> findById(Long id) {
        return jdbcClient.sql(SQL_FIND_USER).param(id).query(new UserRowMapper()).optional();
    }

    @Override
    public List<User> findAll() {
        return jdbcClient.sql(SQL_GET_ALL).query(new UserRowMapper()).list();
    }

    @Override
    public void save(User user) {
        int saved = jdbcClient.sql(SQL_INSERT_USER).params(user.getId(), user.getName(), user.getEmail(), user.getPassword()).update();
        Assert.state(saved == 1 , "An exception error occurred while inserting customer");
    }

    @Override
    public void update(Long id, User user) {
        //"update user set nome = ?, email = ?, senha = ? where id = ?"
        int updated = jdbcClient.sql(SQL_UPDATE_USER).params(user.getName(), user.getEmail(), user.getPassword(), id).update();
        Assert.state(updated == 1, "An exception error occurred while inserting customer");
    }

    @Override
    public void deleteById(Long id) {
        int deleted = jdbcClient.sql(SQL_DELETE_USER).param(id).update();
        Assert.state(deleted == 1, "An exception error occurred while updating User");
    }

    @Override
    public List<User> findPaged(int page, int size) {
       int offset = page * size;
       return  jdbcClient.sql(SQL_PAGER).param(size).param(offset).query(new UserRowMapper()).list();
    }
}
