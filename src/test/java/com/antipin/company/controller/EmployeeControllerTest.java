package com.antipin.company.controller;

import com.antipin.company.exception.EntityNotFoundException;
import com.antipin.company.model.Department;
import com.antipin.company.model.Employee;
import com.antipin.company.service.DepartmentService;
import com.antipin.company.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeControllerTest extends AbstractControllerTest {

    private final String URL = "/api/v1/employees";

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void whenGetOneThenReturn200() throws Exception {
        mockMvc.perform(get(URL + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstname").value("Firstname1"))
                .andExpect(jsonPath("$.lastname").value("Lastname1"))
                .andExpect(jsonPath("$.position").value("Position1"))
                .andExpect(jsonPath("$.salary").value(1000.00))
                .andExpect(jsonPath("$.department.id").value(1))
                .andExpect(jsonPath("$.department.name").value("department 1"));
    }

    @Test
    public void whenGetAllThenReturn200() throws Exception {
        mockMvc.perform(get(URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void whenPostThenCreateAndReturnWithLocation() throws Exception {
        Department department = departmentService.getById(1L);
        Employee newEmployee = new Employee(null,
                "Newfirstname",
                "Newlastname",
                "New position",
                BigDecimal.valueOf(5000.00),
                department);
        MvcResult result = mockMvc.perform(post(URL)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(newEmployee)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.firstname").value(newEmployee.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(newEmployee.getLastname()))
                .andReturn();
        Employee createdEmployee = mapper.readValue(result.getResponse().getContentAsString(), Employee.class);
        Assertions.assertThat(result.getResponse().getHeader("Location")).endsWith(String.valueOf(createdEmployee.getId()));
    }

    @Test
    public void whenPutThenUpdateAndReturn200() throws Exception {
        Employee employee = employeeService.getById(1L);
        employee.setPosition("CEO");
        mockMvc.perform(put(URL)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(employee)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.position").value(employee.getPosition()));
    }

    @Test
    public void whenDeleteThenReturnNoContent() throws Exception {
        mockMvc.perform(delete(URL + "/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertThatThrownBy(() -> employeeService.getById(1L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void whenGetOneProjectionThenReturn200() throws Exception {
        mockMvc.perform(get(URL + "/1/projection"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.fullName").value("Firstname1 Lastname1"))
                .andExpect(jsonPath("$.position").value("Position1"))
                .andExpect(jsonPath("$.departmentName").value("department 1"));
    }

    @Test
    public void whenGetAllProjectionThenReturn200() throws Exception {
        mockMvc.perform(get(URL + "/projection"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$..fullName").exists())
                .andExpect(jsonPath("$..departmentName").exists());
    }

}
