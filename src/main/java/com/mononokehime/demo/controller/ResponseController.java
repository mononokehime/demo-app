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
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.stream.Collectors;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ResponseController {
    private final String version = "1.0";


    @GetMapping("/headers")
    public final Resource customHeader(final HttpServletRequest request, @RequestHeader final HttpHeaders headers) throws SocketException {
        final String response = httpServletRequestToString(request);
        return new Resource(response,
                linkTo(methodOn(ResponseController.class).customHeader(request, headers)).withSelfRel());

//        return new ResponseEntity<>(
//                response, headers, HttpStatus.OK);
    }

    @GetMapping("/version")
    public final  Resource version(@RequestHeader final HttpHeaders headers) throws SocketException {
        return new Resource(version,
                linkTo(methodOn(ResponseController.class).version(headers)).withSelfRel());
    }

    private String httpServletRequestToString(final HttpServletRequest request) throws SocketException {
        final StringBuilder sb = new StringBuilder();


        sb.append("Request Context = [" + request.getContextPath() + "], \n");
        sb.append("Request path info = [" + request.getPathInfo() + "],  \n");
        sb.append("Request query string = [" + request.getQueryString() + "],  \n");
        sb.append("Request remote user = [" + request.getRemoteUser() + "],  \n");
        sb.append("Request session id = [" + request.getRequestedSessionId() + "],  \n");

        sb.append("Request request url = [" + request.getRequestURL().toString() + "],  \n");
        sb.append("Request remote user = [" + request.getRemoteUser() + "],  \n");
        sb.append("Request remote addr = [" + request.getRemoteAddr() + "],  \n");
        sb.append("Request remote host = [" + request.getRemoteHost() + "],  \n");
        sb.append("Request server name = [" + request.getServerName() + "],  \n");

        sb.append("Request Method = [" + request.getMethod() + "],  \n");
        sb.append("Request URL Path = [" + request.getRequestURL() + "],  \n");


        final Enumeration e = NetworkInterface.getNetworkInterfaces();
        while (e.hasMoreElements()) {
            final NetworkInterface networkInterface = (NetworkInterface) e.nextElement();
            final Enumeration enumeration = networkInterface.getInetAddresses();
            while (enumeration.hasMoreElements()) {
                final InetAddress i = (InetAddress) enumeration.nextElement();
                sb.append("Network address: " + i.getHostAddress() + "\n");
                //System.out.println(i.getHostAddress());
            }
        }

        final String headers =
                Collections.list(request.getHeaderNames()).stream()
                        .map(headerName -> headerName + " : " + Collections.list(request.getHeaders(headerName)))
                        .collect(Collectors.joining(", "));

        if (headers.isEmpty()) {
            sb.append("Request headers: NONE,");
        } else {
            sb.append("Request headers: [" + headers + "],  \n");
        }

        final String parameters =
                Collections.list(request.getParameterNames()).stream()
                        .map(p -> p + " : " + Arrays.asList(request.getParameterValues(p)))
                        .collect(Collectors.joining(", "));

        if (parameters.isEmpty()) {
            sb.append("Request parameters: NONE.");
        } else {
            sb.append("Request parameters: [" + parameters + "].  \n");
        }

        return sb.toString();
    }
}
