package com.audit_log.service;

import com.audit_log.model.User;
import com.audit_log.model.UserMapper;
import com.audit_log.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class UserService  implements UserRepository {

    JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_PERSON = "select * from people where id = ?";
    private final String SQL_DELETE_PERSON = "delete from people where id = ?";
    private final String SQL_UPDATE_PERSON = "update people set first_name = ?, last_name = ?, age  = ? where id = ?";
    private final String SQL_GET_ALL = "select * from people";
    private final String SQL_INSERT_PERSON = "insert into people(id, first_name, last_name, age) values(?,?,?,?)";

    @Autowired
    public UserService(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User getById(Long id){
        return (User) jdbcTemplate.queryForObject(SQL_FIND_PERSON, new Object[] {id}, new UserMapper());
    }



}
