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
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */






import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import com.mononokehime.demo.data.Employee;
import com.mononokehime.demo.data.EmployeeNotFoundException;
import com.mononokehime.demo.data.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private EmployeeResourceAssembler assembler;

    //    public EmployeeController(EmployeeRepository repository,
//                       EmployeeResourceAssembler assembler) {
//
//        this.repository = repository;
//        this.assembler = assembler;
//    }
    // Aggregate root
    @GetMapping("/employees")
    public final Resources<Resource<Employee>> all() {

        System.out.println("***************" + repository.findAll().size());

        final List<Resource<Employee>> employees = repository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
        System.out.println("***************" + employees);

        return new Resources<>(employees,
                linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    @PostMapping("/employees")
    public final ResponseEntity<?> newEmployee(@RequestBody final Employee newEmployee) throws URISyntaxException {

        final Resource<Employee> resource = assembler.toResource(repository.save(newEmployee));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    // Single item
    @GetMapping("/employees/{id}")
    public final Resource<Employee> one(@PathVariable final Long id) {

        final Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return assembler.toResource(employee);
    }

    @PutMapping("/employees/{id}")
    public final ResponseEntity<?> replaceEmployee(@RequestBody final Employee newEmployee, @PathVariable final Long id) throws URISyntaxException {

        final Employee updatedEmployee = repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });

        final Resource<Employee> resource = assembler.toResource(updatedEmployee);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/employees/{id}")
    public final ResponseEntity<?> deleteEmployee(@PathVariable final Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
