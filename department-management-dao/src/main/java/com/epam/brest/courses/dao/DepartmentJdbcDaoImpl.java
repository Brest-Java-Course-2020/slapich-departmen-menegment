package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;


@PropertySource(value = {"classpath:sql.properties"})
public class DepartmentJdbcDaoImpl implements DepartmentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentJdbcDaoImpl.class);
    private static final String DEPARTMENT_ID = "departmentId";
    private static final String DEPARTMENT_NAME = "departmentName";

    //CalculatorPrice calculatorPrice = (CalculatorPrice) applicationContext.getBean("calculator");

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${DepartmentSql.findAll}")
    private String findAllSql;
    @Value("${DepartmentSql.getDepartmentById}")
    private String getDepartmentByIdSql;
    @Value("${DepartmentSql.saveDepartment}")
    private String saveDepartmentSql;
    @Value("${DepartmentSql.updateDepartment}")
    private String updateDepartmentSql;
    @Value("${DepartmentSql.deleteUser}")
    private String deleteDepartmentSql;



    public DepartmentJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Department> getDepartments() {
        LOGGER.trace("Get all departments {}", 0);
        List<Department> departments = namedParameterJdbcTemplate
                .query(findAllSql, new DepartmentRowMapper());
        return departments;
    }

    @Override
    public Department getDepartmentById(Integer departmentId) {
        return namedParameterJdbcTemplate.queryForObject(getDepartmentByIdSql, new MapSqlParameterSource(DEPARTMENT_ID, departmentId), new DepartmentRowMapper());
    }

    @Override
    public int addDepartment(Department department) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue(DEPARTMENT_ID, department.getDepartmentId());
        namedParameters.addValue(DEPARTMENT_NAME, department.getDepartmentName());

        namedParameterJdbcTemplate.update(saveDepartmentSql, namedParameters, keyHolder, new String[]{"departmentId"});

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public void updateDepartment(Department department) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue(DEPARTMENT_ID, department.getDepartmentId());
        namedParameters.addValue(DEPARTMENT_NAME, department.getDepartmentName());
        namedParameterJdbcTemplate.update(updateDepartmentSql, namedParameters);

    }

    @Override
    public void deleteDepartment(Integer departmentId) {
        namedParameterJdbcTemplate.update(deleteDepartmentSql, new MapSqlParameterSource(DEPARTMENT_ID, departmentId));

    }

    private class DepartmentRowMapper implements RowMapper<Department> {
        @Override
        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
            Department department = new Department();
            department.setDepartmentId(resultSet.getInt("DEPARTMENT_ID"));
            department.setDepartmentName(resultSet.getString("DEPARTMENT_NAME"));
            return department;
        }
    }
}
