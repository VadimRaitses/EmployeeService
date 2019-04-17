package com.employeeservice.controllers;

import com.employeeservice.exceptions.EntityAlreadyExistsException;
import com.employeeservice.models.Department;
import com.employeeservice.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Raitses Vadim
 */

@RestController
@RequestMapping(path = "/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<?> createDepartment(@RequestBody Department department) throws EntityAlreadyExistsException {
        Department serviceDepartment = departmentService.createDepartment(department);
        return new ResponseEntity<>(serviceDepartment, HttpStatus.OK);
    }
}
