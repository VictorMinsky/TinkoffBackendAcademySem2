package com.tinkoff.rancher.service;

import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SystemService {
    private final BuildProperties buildProperties;
    private final Environment env;
    private final ManagedChannel managedChannel;
    private static volatile boolean isServiceReady = false;

    @Autowired
    private SystemService(BuildProperties buildProperties, Environment env) {
        this.buildProperties = buildProperties;
        this.env = env;

        managedChannel = ManagedChannelBuilder
                .forAddress(this.env.getProperty("grpc.server.address"),
                        Integer.parseInt(Objects.requireNonNull(this.env.getProperty("grpc.server.port"))))
                .usePlaintext()
                .build();
    }

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

    /**
     * Checks readiness of the service.
     * Uses gRPC ConnectivityState to obtain status.
     *
     * @return map of service name, and it's status (1 of 5 statuses)
     * @see ConnectivityState
     */
    public Map<String, String> readinessGRPC() {
        ConnectivityState state = managedChannel.getState(true);
        return Map.of(buildProperties.getName(), state.name());
    }

    /**
     * Checks readiness of the service.
     * Uses gRPC ConnectivityState to obtain status.
     *
     * @return map of service name, and it's status (1 of 5 statuses)
     * @see ConnectivityState
     */
    public Map<String, String> readinessGRPC() {
        ConnectivityState state = managedChannel.getState(true);
        return Map.of(buildProperties.getName(), state.name());
    }
}
