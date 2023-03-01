package com.tinkoff.rancher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SystemService {
    private final BuildProperties buildProperties;
    private static volatile boolean isServiceReady = false;


    /**
     * Changes internal state `isServiceReady` to true
     * to indicate that service is ready.
     */
    public static void changeServiceReadinessToTrue() {
        isServiceReady = true;
    }

    /**
     * Checks readiness of the service
     *
     * @return map of service name, and it's status ("OK")
     */
    public Map<String, String> readiness() {
        return Map.of(buildProperties.getName(), isServiceReady ? "OK" : "NOT OK");
    }
}
