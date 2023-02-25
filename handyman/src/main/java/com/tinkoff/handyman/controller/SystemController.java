package com.tinkoff.handyman.controller;

import com.tinkoff.handyman.service.SystemService;
import io.grpc.ConnectivityState;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/system")
@RequiredArgsConstructor
public class SystemController {
    private final SystemService service;

    /**
     * Checks liveness of the service, returns OK 200
     *
     * @return {@link ResponseEntity} with HTTP Status OK
     */
    @GetMapping("/liveness")
    public ResponseEntity<Void> liveness() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Returns OK 200 and json with service status.
     *
     * @return json with service name, and it's status ("OK")
     * @see SystemService#readiness()
     */
    @GetMapping("/readiness")
    public Map<String, String> readiness() {
        return service.readiness();
    }

    /**
     * Returns OK 200 and json with service status.
     * Uses gRPC ConnectivityState to obtain status.
     *
     * @return json with service name, and it's status (1 of 5 statuses)
     * @see SystemService#readiness()
     * @see ConnectivityState
     */
    @GetMapping("/readinessGRPC")
    public Map<String, String> readinessGRPC() {
        return service.readinessGRPC();
    }
}
