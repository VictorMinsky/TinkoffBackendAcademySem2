package com.tinkoff.landscape.service;

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
     * Changes internal state `isServiceReady` to `status`
     * to indicate that service is ready.
     *
     * @param status to change readiness
     */
    public static void changeServiceReadiness(boolean status) {
        isServiceReady = status;
    }

    /**
     * Checks readiness of the service.
     *
     * @return map of service name, and it's status ("OK" or "Malfunction")
     */
    public Map<String, String> readiness() {
        return Map.of(buildProperties.getName(), isServiceReady ? "OK" : "Malfunction");
    }
}