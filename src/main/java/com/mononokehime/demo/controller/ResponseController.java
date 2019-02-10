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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.stream.Collectors;


@RestController
public class ResponseController {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(ResponseController.class);
    public static final String VERSION = "1.0";


    @GetMapping("/headers")
    public final ResponseEntity<String> customHeader(final HttpServletRequest request, @RequestHeader final HttpHeaders headers) throws SocketException {
        final String response = httpServletRequestToString(request, headers);
//        return new Resource(response,
//                linkTo(methodOn(ResponseController.class).customHeader(request, headers)).withSelfRel());

        return new ResponseEntity<>(
                response, headers, HttpStatus.OK);
    }

    @GetMapping("/version")
    public final  ResponseEntity<String> version(@RequestHeader final HttpHeaders headers) throws SocketException {
        LOGGER.debug("************************** request headers : " + headers);
        String v =  readGitProperties();
        return new ResponseEntity<>(
                v, headers, HttpStatus.OK);
    }

    private String httpServletRequestToString(final HttpServletRequest request, final HttpHeaders headers) throws SocketException {
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

//        final String headers =
//                Collections.list(request.getHeaderNames()).stream()
//                        .map(headerName -> headerName + " : " + Collections.list(request.getHeaders(headerName)))
//                        .collect(Collectors.joining(", "));

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
        LOGGER.debug(sb.toString());
        return sb.toString();
    }

    private String readGitProperties() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("git.properties");
        try {
            return readFromInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return "Version information could not be retrieved";
        }
    }
    private String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
