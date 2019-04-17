package com.employeeservice.services;

import com.employeeservice.exceptions.EntityAlreadyExistsException;
import com.employeeservice.models.Department;


/**
 *  @author  Raitses Vadim
 */


public interface DepartmentService {

    Department createDepartment(Department department) throws EntityAlreadyExistsException;

}
