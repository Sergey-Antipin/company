package com.antipin.company.controller;

import com.antipin.company.model.Department;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DepartmentControllerTest extends AbstractControllerTest {

    private final String URL = "/api/v1/departments";

    @Test
    public void whenGetOneThenReturn200() throws Exception {
        mockMvc.perform(get(URL + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("department 1"));
    }

    @Test
    public void whenGetAllThenReturn200() throws Exception {
        mockMvc.perform(get(URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void whenPostThenCreateNewAndReturnWithLocation() throws Exception {
        Department newDepartment = new Department(null, "New department");
        MvcResult result= mockMvc.perform(post(URL)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(newDepartment)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.name").value(newDepartment.getName()))
                .andReturn();
        Department createdDepartment = mapper.readValue(result.getResponse().getContentAsString(), Department.class);
        Assertions.assertThat(result.getResponse().getHeader("Location")).endsWith(String.valueOf(createdDepartment.getId()));
    }

    @Test
    public void whenPutThenUpdateCorrectly() throws Exception {
        Department departmentToUpdate = new Department(1L, "Updated name");
        mockMvc.perform(put(URL)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(departmentToUpdate)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(departmentToUpdate.getId()))
                .andExpect(jsonPath("$.name").value(departmentToUpdate.getName()));
    }

    @Test
    public void whenDeleteThenReturn201AndDeleteEntity() throws Exception {
        String location = "/1";
        mockMvc.perform(delete(URL + location))
                .andDo(print())
                .andExpect(status().isNoContent());
        mockMvc.perform(get(URL + location))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
