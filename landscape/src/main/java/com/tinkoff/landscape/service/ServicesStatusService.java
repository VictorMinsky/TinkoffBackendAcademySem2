package com.tinkoff.landscape.service;

import com.google.protobuf.Empty;
import com.tinkoff.landscape.dto.ServiceStatusDTO;
import com.tinkoff.proto.ReadinessResponse;
import com.tinkoff.proto.StatusServiceGrpc;
import com.tinkoff.proto.VersionResponse;
import io.grpc.Channel;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelFactory;
import net.devh.boot.grpc.client.config.GrpcChannelsProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Setter
@RequiredArgsConstructor
public class ServicesStatusService {
    private final GrpcChannelsProperties grpcChannelsProperties;
    private final GrpcChannelFactory grpcChannelFactory;

    /**
     * Gets status for each server defined in application.yml
     * Ignores default GLOBAL client.
     *
     * @return Map of services and their {@link ServiceStatusDTO}
     */
    public Map<String, List<ServiceStatusDTO>> getStatuses() {
        Map<String, List<ServiceStatusDTO>> servicesStatuses = new HashMap<>();

        grpcChannelsProperties.getClient()
                .keySet()
                .stream()
                .filter(it -> !it.equals("GLOBAL")) // removes default GLOBAL channel
                .forEach(serviceName -> {
                    try {
                        servicesStatuses.computeIfAbsent(serviceName, key -> new ArrayList<>())
                                .add(getServiceStatusDTO(serviceName));
                    } catch (StatusRuntimeException ignored) {
                    }
                });

        return servicesStatuses;
    }

    private ServiceStatusDTO getServiceStatusDTO(String serviceName) {
        Channel serviceChannel = grpcChannelFactory.createChannel(serviceName);
        StatusServiceGrpc.StatusServiceBlockingStub serviceStatusBlockingStub = StatusServiceGrpc.newBlockingStub(serviceChannel);

        ReadinessResponse readiness = serviceStatusBlockingStub.getReadiness(Empty.getDefaultInstance());
        VersionResponse versionResponse = serviceStatusBlockingStub.getVersion(Empty.getDefaultInstance());

        return ServiceStatusDTO.builder()
                .host(serviceStatusBlockingStub.getChannel().authority())
                .status(readiness.getStatus())
                .artifact(versionResponse.getArtifact())
                .name(versionResponse.getName())
                .group(versionResponse.getGroup())
                .version(versionResponse.getVersion())
                .build();
    }
}
