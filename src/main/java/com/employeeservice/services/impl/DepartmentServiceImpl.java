package com.employeeservice.services.impl;

import com.employeeservice.dao.DaoRepository;
import com.employeeservice.exceptions.EntityAlreadyExistsException;
import com.employeeservice.models.Department;
import com.employeeservice.services.DepartmentService;

import java.util.UUID;


/**
 * @author Raitses Vadim
 */

public class DepartmentServiceImpl implements DepartmentService {

    private final DaoRepository repository;

    public DepartmentServiceImpl(DaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Department createDepartment(Department department) throws EntityAlreadyExistsException {
        return (Department) repository.save(department.setId(generateDepartmentId()));
    }

    String generateDepartmentId() {
        return UUID.randomUUID().toString();
    }

}
