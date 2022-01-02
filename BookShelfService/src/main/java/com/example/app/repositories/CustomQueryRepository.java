package com.example.app.repositories;

import com.example.app.repositories.query.BasicQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomQueryRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public <T, Mapper extends RowMapper<T>> List<T> getEntitesByCriteria(BasicQueryCriteria basicQueryCriteria, Mapper mapper)
    {
        return namedParameterJdbcTemplate.query(basicQueryCriteria.createQuery(), basicQueryCriteria.getParams(), mapper);
    }
}
