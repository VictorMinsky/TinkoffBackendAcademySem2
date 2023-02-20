package com.tinkoff.landscape.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SystemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void livenessReturns200() throws Exception {
        // Given
        String path = "/system/liveness";

        // When
        ResultActions response = mockMvc.perform(get(path));

        // Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void readinessReturns200AndOK() throws Exception {
        // Given
        String path = "/system/readiness";

        // When
        ResultActions response = mockMvc.perform(get(path));

        // Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.LandscapeService").value("OK"));
    }
}
