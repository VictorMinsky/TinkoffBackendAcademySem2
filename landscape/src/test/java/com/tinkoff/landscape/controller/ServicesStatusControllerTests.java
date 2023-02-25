package com.tinkoff.landscape.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class ServicesStatusControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getStatuses() throws Exception {
        // Given
        String path = "/services/statuses";

        // When
        ResultActions response = mockMvc.perform(get(path));

        // Then
        response.andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "RancherService": [
                                {
                                    "host": "localhost:9092",
                                    "status": "OK",
                                    "artifact": "RancherService",
                                    "name": "RancherService",
                                    "group": "com.tinkoff",
                                    "version": "0.1.1"
                                }
                            ],
                            "HandymanService": [
                                {
                                    "host": "localhost:9090",
                                    "status": "OK",
                                    "artifact": "HandymanService",
                                    "name": "HandymanService",
                                    "group": "com.tinkoff",
                                    "version": "0.1.1"
                                }
                            ]
                        }
                        """));
    }
}
