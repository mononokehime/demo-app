//package com.mononokehime.demo.config;
//
///*-
// * #%L
// * Spring Boot Demo App
// * %%
// * Copyright (C) 2019 Mononokehime
// * %%
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// * #L%
// */
//
//import io.jaegertracing.internal.JaegerTracer;
//import io.jaegertracing.internal.reporters.InMemoryReporter;
//import io.jaegertracing.internal.samplers.ConstSampler;
//import io.jaegertracing.spi.Reporter;
//import io.jaegertracing.spi.Sampler;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@ConditionalOnProperty(value = "opentracing.jaeger.enabled", havingValue = "false", matchIfMissing = false)
//@Configuration
//public final class MyTracerConfiguration {
//
//    @Bean
//    public io.opentracing.Tracer jaegerTracer() {
//        final Reporter reporter = new InMemoryReporter();
//        final Sampler sampler = new ConstSampler(false);
//        return new JaegerTracer.Builder("untraced-service")
//                .withReporter(reporter)
//                .withSampler(sampler)
//                .build();
//    }
//}
