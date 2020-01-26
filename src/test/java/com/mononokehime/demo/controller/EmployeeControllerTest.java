package com.mononokehime.demo.controller;

/*-
 * #%L
 * Demo Spring Boot Application
 * %%
 * Copyright (C) 2018 - 2019 Monononoke Organization
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mononokehime.demo.DemoApplication;
import com.mononokehime.demo.data.Employee;
import com.mononokehime.demo.data.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private EmployeeModelAssembler assembler;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void givenEmployees_whenAll_thenReturnJsonArray()
            throws Exception {

        MvcResult result = mvc.perform(get("/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.employees", hasSize(2)))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/employees"))).andReturn();

       // String content = result.getResponse().getContentAsString();
      //  System.out.print(content);
    }

    @Test
    public void givenEmployees_whenRequestOne_thenReturnJsonArray()
            throws Exception {
        Integer id = new Integer(1);
        MvcResult result = mvc.perform(get("/employees/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is("Bilbo Baggins"))).andReturn();
    }

    @Test
    public void givenEmployees_whenRequestOne_thenReturnNotFound()
            throws Exception {
        Integer id = new Integer(26);
        mvc.perform(get("/employees/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext
    public void createEmployee_whenCreateOne_thenReturnJsonArray()
            throws Exception {
        Employee employee = new Employee("Sam", "Gangee", "ring bearer");
        String json = mapper.writeValueAsString(employee);
        MvcResult result = mvc.perform(post("/employees")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.role", is(employee.getRole())))
                .andExpect(jsonPath("$.name", is(employee.getName())))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/employees/3")))
                .andExpect(jsonPath("$._links.employees.href", is("http://localhost/employees")))
                .andReturn();
    }

    @Test
    @DirtiesContext
    public void updateEmployee_whenUpdateOne_thenReturnJsonArray()
            throws Exception {
        Employee employee = new Employee("Bilbo", "Baggins", "ex ring bearer");
        String json = mapper.writeValueAsString(employee);
        MvcResult result = mvc.perform(put("/employees/1")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.role", is(employee.getRole())))
                .andExpect(jsonPath("$.name", is(employee.getName())))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/employees/1")))
                .andExpect(jsonPath("$._links.employees.href", is("http://localhost/employees")))
                .andReturn();
    }

    @Test
    public void updateEmployee_whenUpdateOne_thenThrowNotAllowed()
            throws Exception {
        Employee employee = new Employee("Bilbo", "Baggins", "ex ring bearer");
        String json = mapper.writeValueAsString(employee);
        MvcResult result = mvc.perform(post("/employees/1")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

}
