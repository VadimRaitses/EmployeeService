package com.employeeservice.services;

import com.employeeservice.exceptions.BadEntityException;
import com.employeeservice.exceptions.EntityAlreadyExistsException;
import com.employeeservice.exceptions.EntityNotFoundException;
import com.employeeservice.models.Employee;


/**
 * @author Raitses Vadim
 */


public interface EmployeeService {

    Employee createEmployee(Employee employee) throws EntityAlreadyExistsException, EntityNotFoundException, BadEntityException;

    Employee getEmployeeById(String id) throws EntityNotFoundException;

    boolean updateEmployee(String id, Employee employee) throws EntityNotFoundException;

    boolean deleteEmployee(String id) throws EntityNotFoundException;
}
