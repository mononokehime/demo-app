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

import com.mononokehime.demo.data.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
class ChainController {

    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot!";
    }
    @RequestMapping("/chaining")
    public String chaining() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8000/hello", String.class);
        return "Chaining + " + response.getBody();
    }
    @RequestMapping("/chaining-employee")
    public Resources<Resource<Employee>> chainEmployees() {
        return restTemplate.getForObject("http://localhost:8000/employees", Resources.class);
      //  ResponseEntity<Resources> response = restTemplate.getForEntity("http://localhost:8000/employees", Resources.class);

    }
}
