package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Department;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentJdbcDaoImpMocklTest {

    @InjectMocks
    private DepartmentJdbcDaoImpl departmentDao;

    // DepartmentDao departmentDao;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


//    @BeforeEach
//    void before(){
//        namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
//        departmentDao = new DepartmentJdbcDaoImpl(namedParameterJdbcTemplate);
//    }




    @AfterEach
    void after(){
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }// проверка на то что метод был вызван для нашего обхекта

   @Test
    public void getDepartments() {
        Department department = new Department();

        when(namedParameterJdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(Collections.singletonList(Arrays.asList(department)));
        // когда идет обращение к моку namedParameterJdbcTemplate, c тами параметрами  anyString и any(RowMapper.class), то вернуть коллекцию


        List<Department> departments = departmentDao.getDepartments();
        assertNotNull(departments); // проверка статуса результата на NuLL
        assertEquals(1, departments.size()); // проверка на то что пришла одна коллекция
       Department dep = departments.get(0);
       assertNotNull(dep);

        assertSame(dep, department); // сравнивает два объекта и проверяем что это один и тот же обхект

        // тестируктся что только вызывается метод query
        //Mockito.verify(namedParameterJdbcTemplate, Mockito.times(3)).query(anyString(), any(RowMapper.class));
       // проверить наличие ввденной строки sql на совместимость ее в имлементации
       Mockito.verify(namedParameterJdbcTemplate).query(
               eq("SELECT d.departmentId, d.departmentName FROM department d ORDER BY d.departmentName"), any(RowMapper.class));
    }

    @Test
    public void getDepartmentById() {

    }

    @Test
    public void addDepartment() {
    }

    @Test
    public void updateDepartment() {
    }

    @Test
    public void deleteDepartment() {
    }

}