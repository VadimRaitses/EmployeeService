package com.employeeservice.services.impl;

import com.employeeservice.dao.DaoRepository;
import com.employeeservice.exceptions.EntityAlreadyExistsException;
import com.employeeservice.models.Department;
import com.employeeservice.services.SequenceGeneratorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DepartmentServiceImplTest {

    private static DepartmentServiceImpl classUnderTest;
    private final static String departmentMockedId = "id";
    private final DaoRepository repository = mock(DaoRepository.class);
    private final SequenceGeneratorService sequenceMock = mock(SequenceGeneratorServiceImpl.class);

    @Before
    public void setUp() {
        classUnderTest = new DepartmentServiceImpl(repository, sequenceMock);
    }

    @Test
    public void createDepartmentSuccess() throws Exception {
        when(sequenceMock.generateSequence(Department.SEQUENCE_NAME)).thenReturn("1");
        Department expected = new Department().setName("department").setId(departmentMockedId);
        when(repository.save(expected)).thenReturn(expected);
        Department actual = classUnderTest.createDepartment(expected);
        Assert.assertEquals(expected, actual);
    }


    @Test(expected = EntityAlreadyExistsException.class)
    public void createDepartmentException() throws Exception {
        when(sequenceMock.generateSequence(Department.SEQUENCE_NAME)).thenReturn("1");
        Department expected = new Department().setName("department").setId(departmentMockedId);
        when(repository.save(expected)).thenThrow(new EntityAlreadyExistsException("exists"));
        classUnderTest.createDepartment(expected);
    }


//    class DepartmentServiceImplMock extends DepartmentServiceImpl {
//
//        DepartmentServiceImplMock(DaoRepository repository) {
//            super(repository);
//        }
//
//        @Override
//        String generateDepartmentId() {
//            return departmentMockedId;
//        }
//    }
}