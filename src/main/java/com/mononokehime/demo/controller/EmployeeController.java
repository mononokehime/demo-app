package com.mononokehime.demo.controller;

/*-
 * #%L
 * Spring Boot Demo App
 * %%
 * Copyright (C) 2019 Mononokehime
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



import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import com.mononokehime.demo.data.Employee;
import com.mononokehime.demo.data.EmployeeNotFoundException;
import com.mononokehime.demo.data.EmployeeRepository;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@Slf4j
class EmployeeController {

    @Autowired
    private Environment env;

    public String getGoogleKey() {
        return env.getProperty("fake-key");
    }
    private final EmployeeRepository repository;

    private final EmployeeModelAssembler assembler;

    EmployeeController(final EmployeeRepository repository,
                       final EmployeeModelAssembler assembler) {

        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    @GetMapping("/employees")
    CollectionModel<EntityModel<Employee>> all() {
        log.debug("********** entered all /employees" + getGoogleKey());
        List<EntityModel<Employee>> employees = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(employees,
                linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    @Timed("employees-create")
    @PostMapping("/employees")
    ResponseEntity<?> newEmployee(@RequestBody final Employee newEmployee) throws URISyntaxException {

        EntityModel<Employee> resource = assembler.toModel(repository.save(newEmployee));
        //resource.getLink("employees").toString()
        Link link = linkTo(EmployeeController.class).slash(newEmployee.getId()).withSelfRel().withRel("employees");
        return ResponseEntity
                .created(new URI(link.getHref()))
                .body(resource);
    }

    // Single item

    @GetMapping("/employees/{id}")
    EntityModel<Employee> one(@PathVariable final Long id) {
        log.debug("********* entered one /employees/{id}");
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return assembler.toModel(employee);
    }

    @Timed("employees-update")
    @PutMapping("/employees/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody final Employee newEmployee, @PathVariable final Long id) throws URISyntaxException {

        Employee updatedEmployee = repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });

        EntityModel<Employee> resource = assembler.toModel(updatedEmployee);
        Link link = linkTo(EmployeeController.class).slash(newEmployee.getId()).withSelfRel().withRel("employees");
        return ResponseEntity
                .created(new URI(link.getHref()))
                .body(resource);
    }

    @Timed("employee-delete")
    @DeleteMapping("/employees/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable final Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
