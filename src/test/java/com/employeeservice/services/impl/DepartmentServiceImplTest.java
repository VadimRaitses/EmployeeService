package com.employeeservice.services.impl;

import com.employeeservice.dao.DaoRepository;
import com.employeeservice.exceptions.EntityAlreadyExistsException;
import com.employeeservice.models.Department;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;


public class DepartmentServiceImplTest {

    private static DepartmentServiceImplMock classUnderTest;
    private final static String departmentMockedId = "id";
    private final DaoRepository repository = Mockito.mock(DaoRepository.class);

    @Before
    public void setUp() {
        classUnderTest = new DepartmentServiceImplMock(repository);
    }

    @Test
    public void createDepartmentSuccess() throws Exception {
        Department expected = new Department().setName("department").setId(departmentMockedId);
        when(repository.save(expected)).thenReturn(expected);
        Department actual = classUnderTest.createDepartment(expected);
        Assert.assertEquals(expected, actual);
    }


    @Test(expected = EntityAlreadyExistsException.class)
    public void createDepartmentException() throws Exception {
        Department expected = new Department().setName("department").setId(departmentMockedId);
        when(repository.save(expected)).thenThrow(new EntityAlreadyExistsException("exists"));
        classUnderTest.createDepartment(expected);
    }


    class DepartmentServiceImplMock extends DepartmentServiceImpl {

        DepartmentServiceImplMock(DaoRepository repository) {
            super(repository);
        }

        @Override
        String generateDepartmentId() {
            return departmentMockedId;
        }
    }
}