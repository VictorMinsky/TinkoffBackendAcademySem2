package com.tinkoff.rancher.service;

import com.google.protobuf.Empty;
import com.tinkoff.proto.ReadinessResponse;
import com.tinkoff.proto.StatusServiceGrpc;
import com.tinkoff.proto.VersionResponse;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.info.BuildProperties;

@GrpcService
@RequiredArgsConstructor
public class StatusServiceImpl extends StatusServiceGrpc.StatusServiceImplBase {
    private final BuildProperties buildProperties;
    private final SystemService systemService;

    /**
     * Gets version of the service.
     *
     * @param request          a generic empty message
     * @param responseObserver observer that can be notified by underlying stream
     */
    @Override
    public void getVersion(Empty request, StreamObserver<VersionResponse> responseObserver) {
        responseObserver.onNext(getVersion());
        responseObserver.onCompleted();
    }

    /**
     * Gets readiness of the service.
     *
     * @param request          a generic empty message
     * @param responseObserver observer that can be notified by underlying stream
     */
    @Override
    public void getReadiness(Empty request, StreamObserver<ReadinessResponse> responseObserver) {
        ReadinessResponse response = ReadinessResponse.newBuilder()
                .setStatus(systemService.readiness().get(buildProperties.getName()))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private VersionResponse getVersion() {
        return VersionResponse
                .newBuilder()
                .setArtifact(buildProperties.getArtifact())
                .setName(buildProperties.getName())
                .setGroup(buildProperties.getGroup())
                .setVersion(buildProperties.getVersion())
                .build();
    }
}
