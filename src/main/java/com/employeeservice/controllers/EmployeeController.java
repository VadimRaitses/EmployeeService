package com.employeeservice.controllers;

import com.employeeservice.exceptions.BadEntityException;
import com.employeeservice.exceptions.EntityAlreadyExistsException;
import com.employeeservice.exceptions.EntityNotFoundException;
import com.employeeservice.models.Employee;
import com.employeeservice.models.ErrorDetails;
import com.employeeservice.services.EmployeeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @author Raitses Vadim
 */


@RestController
@RequestMapping(path = "/employee")
@Api(value = "employee", description = "Operations for employees management ")
public class EmployeeController {

    private final EmployeeService employeeService;


    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @ApiOperation(value = "Create Employee", response = Employee.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee Created Successfully", response = Employee.class),
            @ApiResponse(code = 400, message = "entity already exists", response = ErrorDetails.class),
            @ApiResponse(code = 403, message = "Access Denied", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "department  with id %s not found", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "wrong department entity", response = ErrorDetails.class)
    }
    )
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    )
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) throws EntityNotFoundException, BadEntityException, EntityAlreadyExistsException {
        Employee serviceEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(serviceEmployee, HttpStatus.OK);
    }


    @ApiOperation(value = "Get Employee by id", response = Employee.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Employee.class),
            @ApiResponse(code = 403, message = "Access Denied", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "entity with id %s not found", response = ErrorDetails.class)
    }
    )
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    )
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") String id) throws EntityNotFoundException {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Update Employee by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = ""),
            @ApiResponse(code = 403, message = "Access Denied", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "entity with id %s not updated", response = ErrorDetails.class)
    }
    )
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")

    )
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateEmployee(@PathVariable("id") String id, @RequestBody Employee employee) throws EntityNotFoundException {
        employeeService.updateEmployee(id, employee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Delete Employee by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = ""),
            @ApiResponse(code = 403, message = "Access Denied", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "entity with id %s not deleted", response = ErrorDetails.class)
    }
    )
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")

    )
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") String id) throws EntityNotFoundException {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
