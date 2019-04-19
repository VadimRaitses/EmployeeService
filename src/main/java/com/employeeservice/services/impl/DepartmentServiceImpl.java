package com.employeeservice.services.impl;

import com.employeeservice.dao.DaoRepository;
import com.employeeservice.exceptions.EntityAlreadyExistsException;
import com.employeeservice.models.Department;
import com.employeeservice.services.DepartmentService;
import com.employeeservice.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.UUID;


/**
 * @author Raitses Vadim
 */

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DaoRepository repository;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public DepartmentServiceImpl(@NotNull @Qualifier("mongoRepository") DaoRepository repository, SequenceGeneratorService sequenceGeneratorService) {
        this.repository = repository;
        this.sequenceGeneratorService = sequenceGeneratorService;

    }

    @Override
    public Department createDepartment(Department department) throws EntityAlreadyExistsException {
        return (Department) repository.save(department.setId(sequenceGeneratorService.generateSequence(Department.SEQUENCE_NAME)));
    }

}
