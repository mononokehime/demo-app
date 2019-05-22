package com.mononokehime.demo;

/*-
 * #%L
 * demo-app
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


import io.jaegertracing.Configuration;
import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;


/*
 * https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html
 */
@SpringBootApplication
@ComponentScan("com.mononokehime.demo")
@Slf4j
public class DemoApplication {




    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
//    @Bean
//    public static Tracer getTracer() {
//        Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv().withType("const").withParam(1);
//        Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
//        Configuration config = new Configuration("demo-app").withSampler(samplerConfig).withReporter(reporterConfig);
//        return config.getTracer();
//    }
    public static void main(final String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

