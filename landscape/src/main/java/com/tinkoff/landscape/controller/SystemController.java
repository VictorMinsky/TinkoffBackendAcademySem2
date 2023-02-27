package com.tinkoff.landscape.controller;

import com.tinkoff.landscape.service.SystemService;
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
}
