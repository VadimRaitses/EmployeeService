package com.employeeservice.controllers;

import com.employeeservice.exceptions.EntityAlreadyExistsException;
import com.employeeservice.models.Department;
import com.employeeservice.models.Employee;
import com.employeeservice.models.ErrorDetails;
import com.employeeservice.services.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "department", description = "Operations for department management ")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @ApiOperation(value = "Create Department", response = Department.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Department Created Successfully",response = Department.class),
            @ApiResponse(code = 400, message = "entity already exists",response = ErrorDetails.class),
            @ApiResponse(code = 403, message = "Access Denied",response = ErrorDetails.class),
    }
    )
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<?> createDepartment(@RequestBody Department department) throws EntityAlreadyExistsException {
        Department serviceDepartment = departmentService.createDepartment(department);
        return new ResponseEntity<>(serviceDepartment, HttpStatus.OK);
    }
}
