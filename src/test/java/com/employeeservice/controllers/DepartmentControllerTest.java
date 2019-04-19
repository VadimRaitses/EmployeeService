package com.employeeservice.controllers;

import com.employeeservice.exceptions.EntityAlreadyExistsException;
import com.employeeservice.models.Department;
import com.employeeservice.services.DepartmentService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-dev.properties")
@Ignore
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final  Gson gson = new Gson();

    @Mock
    DepartmentService departmentservice;

    @InjectMocks
    DepartmentController controllerUnderTest;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
        controllerUnderTest = new DepartmentController(departmentservice);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();

    }

    @Test
    public void createDepartmentSuccess() throws Exception {
        Department expected = new Department("id", "department");
        when(departmentservice.createDepartment(isA(Department.class))).thenReturn(expected);
        mockMvc
                .perform(post("/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(gson.toJson(expected)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expected.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expected.getName()));
    }

    @Test
    public void createDepartmentException() throws Exception {
        when(departmentservice.createDepartment(isA(Department.class))).thenThrow(new EntityAlreadyExistsException("this entity exists"));
        mockMvc
                .perform(post("/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(gson.toJson(new Department().setName("test"))))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}