package com.employeeservice.controllers;

import com.employeeservice.exceptions.BadEntityException;
import com.employeeservice.exceptions.EntityAlreadyExistsException;
import com.employeeservice.exceptions.EntityNotFoundException;
import com.employeeservice.models.Employee;
import com.employeeservice.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @author Raitses Vadim
 */


@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    private final EmployeeService employeeService;


    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) throws EntityNotFoundException, BadEntityException, EntityAlreadyExistsException {
        Employee serviceEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(serviceEmployee, HttpStatus.OK);
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") String id) throws EntityNotFoundException {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateEmployee(@PathVariable("id") String id, @RequestBody Employee employee) throws EntityNotFoundException {
        employeeService.updateEmployee(id, employee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") String id) throws EntityNotFoundException {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
