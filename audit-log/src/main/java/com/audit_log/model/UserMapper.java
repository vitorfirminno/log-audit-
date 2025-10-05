package com.audit_log.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper {

    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User(resultSet.getLong("id"),
                resultSet.getString("nome"),
                resultSet.getString("email"),
                resultSet.getString("senha"));
        return user;
    }
}
