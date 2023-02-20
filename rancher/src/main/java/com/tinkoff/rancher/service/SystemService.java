package com.tinkoff.rancher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SystemService {
    private final BuildProperties buildProperties;

    /**
     * Checks readiness of the service
     *
     * @return map of service name, and it's status ("OK")
     */
    public Map<String, String> readiness() {
        return Map.of(buildProperties.getName(), "OK");
    }
}
