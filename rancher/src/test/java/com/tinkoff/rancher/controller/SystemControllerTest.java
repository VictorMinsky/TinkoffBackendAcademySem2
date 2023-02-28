package com.tinkoff.rancher.controller;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class SystemControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void initialiseRestAssuredMockMvcWebApplicationContext() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void livenessReturns200() {
        String path = "/system/liveness";

        given()
                .when()
                .get(path)
                .then()
                .statusCode(OK.value())
                .expect(jsonPath("$").doesNotExist());
    }

    @Test
    void readinessReturns200AndOK() {
        String path = "/system/readiness";

        given()
                .when()
                .get(path)
                .then()
                .statusCode(OK.value())
                .expect(jsonPath("$.RancherService").value("OK"));
    }

    @Test
    void readinessGRPCReturns200AndREADY() throws Exception {
        // Given
        String path = "/system/readinessGRPC";

        // When
        ResultActions response = mockMvc.perform(get(path));
        while (true) {
            String responseJSON = response.andReturn().getResponse().getContentAsString();
            if (responseJSON.contains("IDLE") || responseJSON.contains("CONNECTING")) {
                response = mockMvc.perform(get(path));
            } else {
                break;
            }
        }

        // Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.RancherService").value("READY"));
    }

    @Test
    void forceMalfunction() {
        String pathMalfunction = "/system/forceMalfunction";
        String pathReadiness = "/system/readiness";

        given()
                .when()
                .post(pathMalfunction)
                .then()
                .statusCode(OK.value());

        given()
                .when()
                .get(pathReadiness)
                .then()
                .statusCode(SERVICE_UNAVAILABLE.value());
    }

    @Test
    void forceMalfunctionAndBack() {
        String pathMalfunctionTrue = "/system/forceMalfunction";
        String pathMalfunctionFalse = "/system/forceMalfunction?status=false";
        String pathReadiness = "/system/readiness";

        given()
                .when()
                .post(pathMalfunctionTrue)
                .then()
                .statusCode(OK.value());

        given()
                .when()
                .get(pathReadiness)
                .then()
                .statusCode(SERVICE_UNAVAILABLE.value());

        given()
                .when()
                .post(pathMalfunctionFalse)
                .then()
                .statusCode(OK.value());

        given()
                .when()
                .get(pathReadiness)
                .then()
                .statusCode(OK.value())
                .expect(jsonPath("$.RancherService").value("OK"));
    }
}
