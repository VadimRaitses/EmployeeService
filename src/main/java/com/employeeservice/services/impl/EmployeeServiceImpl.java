package com.employeeservice.services.impl;

import com.employeeservice.dao.DaoRepository;
import com.employeeservice.exceptions.BadEntityException;
import com.employeeservice.exceptions.EntityAlreadyExistsException;
import com.employeeservice.exceptions.EntityNotFoundException;
import com.employeeservice.models.Department;
import com.employeeservice.models.Employee;
import com.employeeservice.models.EmployeeState;
import com.employeeservice.services.EmployeeService;
import com.employeeservice.services.QueueService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;


/**
 * @author Raitses Vadim
 */

@Service
public class EmployeeServiceImpl implements EmployeeService {


    private final DaoRepository daoRepository;
    private final QueueService<String> queueService;

    @Autowired
    public EmployeeServiceImpl(@NotNull @Qualifier("mongoRepository") DaoRepository repository,
                               @NotNull QueueService<String> queueService) {
        this.daoRepository = repository;
        this.queueService = queueService;

    }


    @Override
    public Employee createEmployee(Employee employee) throws EntityAlreadyExistsException, EntityNotFoundException, BadEntityException {


        String departmentId = Optional.ofNullable(employee.getDepartment())
                .map(Department::getId)
                .orElseThrow(() -> new BadEntityException("wrong department entity"));


        Optional.ofNullable(daoRepository.exists(departmentId, Department.class))
                .orElseThrow(() -> new EntityNotFoundException(String.format("department  with id %s not found", departmentId)));

        Employee mongoEmployee = (Employee) daoRepository.save(employee.setId(generateEmployeeId()));
        sendStateMessage(employee.getId(), EmployeeState.CREATED);
        return mongoEmployee;
    }

    private void sendStateMessage(String employeeId, EmployeeState state) {
        JsonObject objectData = new JsonObject();
        objectData.addProperty("entityId", employeeId);
        objectData.addProperty("state", state.getState());
        queueService.sendMessage("create", objectData.toString());
    }

    @Override
    public Employee getEmployeeById(String id) throws EntityNotFoundException {
        return (Employee) Optional.ofNullable(daoRepository.get(id, Employee.class))
                .orElseThrow(() -> new EntityNotFoundException(String.format("entity with id %s not found", id)));
    }

    @Override
    public boolean updateEmployee(String id, Employee employee) throws EntityNotFoundException {
        if (daoRepository.update(id, employee, Employee.class)) {
            sendStateMessage(id, EmployeeState.UPDATED);
            return true;
        }
        throw new EntityNotFoundException(String.format("entity with id %s not updated", id));
    }

    @Override
    public boolean deleteEmployee(String id) throws EntityNotFoundException {
        if (daoRepository.delete(id, Employee.class)) {
            sendStateMessage(id, EmployeeState.DELETED);
            return true;
        }
        throw new EntityNotFoundException(String.format("entity with id %s not deleted", id));

    }


    String generateEmployeeId() {
        return UUID.randomUUID().toString();
    }

}
