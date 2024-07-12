package com.antipin.company.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = "spring.config.location=classpath:application-test.properties")
@AutoConfigureMockMvc
@Transactional
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    protected final ObjectMapper mapper = new ObjectMapper();
}
