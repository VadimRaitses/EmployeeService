package com.employeeservice.controllers;

import com.employeeservice.exceptions.BadEntityException;
import com.employeeservice.exceptions.EntityAlreadyExistsException;
import com.employeeservice.exceptions.EntityNotFoundException;
import com.employeeservice.models.Department;
import com.employeeservice.models.Employee;
import com.employeeservice.services.EmployeeService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-dev.properties")
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final Gson gson = new Gson();

    @Mock
    EmployeeService employeeService;


    @InjectMocks
    EmployeeController controllerUnderTest;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
        controllerUnderTest = new EmployeeController(employeeService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();

    }

    @Test
    public void createEmployeeSuccess() throws Exception {
        Employee expected = new Employee()
                .setId("id")
                .setEmail("email")
                .setFullName("full name");
        when(employeeService.createEmployee(isA(Employee.class))).thenReturn(expected);
        mockMvc
                .perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(gson.toJson(expected)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expected.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(expected.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value(expected.getFullName()));

    }


    @Test
    public void createEmployeeEntityAlreadyExistsException() throws Exception {
        when(employeeService.createEmployee(isA(Employee.class))).thenThrow(new EntityAlreadyExistsException(" exists "));
        mockMvc
                .perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(gson.toJson(new Employee()
                                .setId("id")
                                .setEmail("email")
                                .setFullName("full name"))))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }


    @Test
    public void createEmployeeEntityNotFoundException() throws Exception {
        when(employeeService.createEmployee(isA(Employee.class))).thenThrow(new EntityNotFoundException(" not found "));
        mockMvc
                .perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(gson.toJson(new Employee()
                                .setId("id")
                                .setEmail("email")
                                .setFullName("full name"))))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void createEmployeeBadEntityException() throws Exception {
        when(employeeService.createEmployee(isA(Employee.class))).thenThrow(new BadEntityException(" bad entity found "));
        mockMvc
                .perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(gson.toJson(new Employee()
                                .setId("id")
                                .setEmail("email")
                                .setFullName("full name").setDepartment(new Department()))))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    public void getEmployeeByIdSuccess() throws Exception {
        String emId = "id";
        Employee expected = new Employee()
                .setId("id")
                .setEmail("email")
                .setFullName("full name");
        when(employeeService.getEmployeeById(emId)).thenReturn(expected);
        mockMvc
                .perform(get("/employee/" + emId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expected.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(expected.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value(expected.getFullName()));
    }


    @Test
    public void getEmployeeByIdEntityNotFoundException() throws Exception {
        String emId = "id";
        when(employeeService.getEmployeeById(emId)).thenThrow(new EntityNotFoundException("not found"));
        mockMvc
                .perform(get("/employee/" + emId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    public void updateEmployeeSuccess() throws Exception {

        Employee expected = new Employee()
                .setId("id")
                .setEmail("email")
                .setFullName("full name");
        when(employeeService.updateEmployee(any(String.class), any(Employee.class))).thenReturn(true);


        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/employee/id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(gson.toJson(expected));
        mockMvc
                .perform(builder)
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void updateEmployeeEntityNotFoundException() throws Exception {

        Employee expected = new Employee()
                .setId("id")
                .setEmail("email")
                .setFullName("full name");
        when(employeeService.updateEmployee(any(String.class), any(Employee.class))).thenThrow(new EntityNotFoundException("not found"));


        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/employee/id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(gson.toJson(expected));
        mockMvc
                .perform(builder)
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    public void deleteEmployeeSuccess() throws Exception {
        when(employeeService.deleteEmployee("id")).thenReturn(true);
        mockMvc
                .perform(delete("/employee/id"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void deleteEmployeeEntityNotFoundException() throws Exception {
        when(employeeService.deleteEmployee("id")).thenThrow(new EntityNotFoundException("not found"));
        mockMvc
                .perform(delete("/employee/id"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}