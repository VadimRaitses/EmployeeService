package com.employeeservice.services.impl;

import com.employeeservice.dao.DaoRepository;
import com.employeeservice.exceptions.BadEntityException;
import com.employeeservice.exceptions.EntityAlreadyExistsException;
import com.employeeservice.exceptions.EntityNotFoundException;
import com.employeeservice.models.Department;
import com.employeeservice.models.Employee;
import com.employeeservice.services.QueueService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class EmployeeServiceImplTest {


    private static EmployeeServiceImplMock classUnderTest;
    private final static String employeeMockedId = "id";
    private final DaoRepository repository = Mockito.mock(DaoRepository.class);
    private final static QueueService queueService = Mockito.mock(QueueService.class);

    @Before
    public void setUp() {
        classUnderTest = new EmployeeServiceImplMock(repository, queueService);
    }

    @Test
    public void createEmployeeSuccess() throws Exception {
        Employee expected = new Employee().setId(employeeMockedId).setDepartment(new Department().setId("id"));
        when(repository.exists("id", Department.class)).thenReturn(true);
        when(repository.save(expected)).thenReturn(expected);
        Employee actual = classUnderTest.createEmployee(expected);
        Assert.assertEquals(expected, actual);
    }


    @Test(expected = BadEntityException.class)
    public void createEmployeeBadEntityException() throws Exception {
        Employee expected = new Employee().setId(employeeMockedId);
        when(repository.exists("id", Department.class)).thenReturn(true);
        when(repository.save(expected)).thenReturn(expected);
        classUnderTest.createEmployee(expected);

    }


    @Test(expected = EntityNotFoundException.class)
    public void createEmployeeEntityNotFoundException() throws Exception {
        Employee expected = new Employee().setId(employeeMockedId).setDepartment(new Department().setId("id"));
        when(repository.save(expected)).thenReturn(expected);
        classUnderTest.createEmployee(expected);

    }


    @Test(expected = EntityAlreadyExistsException.class)
    public void createEmployeeEntityAlreadyExistsException() throws Exception {
        Employee expected = new Employee().setId(employeeMockedId).setDepartment(new Department().setId("id"));
        when(repository.exists("id", Department.class)).thenReturn(true);
        when(repository.save(expected)).thenThrow(new EntityAlreadyExistsException(""));
        classUnderTest.createEmployee(expected);

    }


    @Test
    public void getEmployeeByIdSuccess() throws Exception {
        Employee expected = new Employee().setId(employeeMockedId).setDepartment(new Department().setId("id"));
        when(repository.get("id", Employee.class)).thenReturn(expected);
        Employee actual = classUnderTest.getEmployeeById("id");
        Assert.assertEquals(expected, actual);
    }


    @Test(expected = EntityNotFoundException.class)
    public void getEmployeeByIdNotFoundException() throws Exception {
        classUnderTest.getEmployeeById("id");
    }


    @Test
    public void getEmployeeByIdNotFound() throws Exception {
        Employee expected = new Employee().setId(employeeMockedId).setDepartment(new Department().setId("id"));
        when(repository.get("id", Employee.class)).thenReturn(expected);
        Employee actual = classUnderTest.getEmployeeById("id");
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void updateEmployeeSuccess() throws Exception {
        Employee expected = new Employee().setId(employeeMockedId).setDepartment(new Department().setId("id"));
        when(repository.update("id",expected, Employee.class)).thenReturn(true);
        boolean actual = classUnderTest.updateEmployee("id",expected);
        Assert.assertTrue(actual);
    }



    @Test(expected= EntityNotFoundException.class)
    public void updateEmployeeEntityNotFoundException() throws Exception {
        Employee expected = new Employee().setId(employeeMockedId).setDepartment(new Department().setId("id"));
        //when(repository.update("id",expected, Employee.class)).thenReturn(true);
        classUnderTest.updateEmployee("id",expected);

    }

    @Test
    public void deleteEmployeeSucess() throws Exception {
        when(repository.delete("id", Employee.class)).thenReturn(true);
        boolean actual = classUnderTest.deleteEmployee("id");
        Assert.assertTrue(actual);

    }


    @Test(expected= EntityNotFoundException.class)
    public void deleteEmployeeEntityNotFoundException() throws Exception {
        classUnderTest.deleteEmployee("id");

    }


    class EmployeeServiceImplMock extends EmployeeServiceImpl {

        EmployeeServiceImplMock(DaoRepository repository,
                                QueueService<String> queueService) {
            super(repository, queueService);
        }

        @Override
        String generateEmployeeId() {
            return employeeMockedId;
        }
    }

}