package com.employeeservice.dao;


import com.employeeservice.exceptions.EntityAlreadyExistsException;

import java.util.List;


/**
 * @author Raitses Vadim
 */

public interface DaoRepository {

    Object get(String id, Class entityClass);

    List getByQuery(Object query, Class entityClass);

    Object save(Object t) throws EntityAlreadyExistsException;

    boolean update(String id, Object t, Class entityClass);

    boolean delete(String id, Class entityClass);

    Object exists(String id, Class entityClass);

}
