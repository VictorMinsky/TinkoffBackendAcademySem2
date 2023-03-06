package com.tinkoff.handyman.service;

import com.google.protobuf.Empty;
import com.tinkoff.proto.ReadinessResponse;
import com.tinkoff.proto.StatusServiceGrpc;
import com.tinkoff.proto.VersionResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {
        "grpc.server.in-process-name=test",
        "grpc.server.port=-1",
        "grpc.client.inProcess.address=in-process:test"
})
@DirtiesContext
public class StatusServiceImplTest {
    @GrpcClient("inProcess")
    private StatusServiceGrpc.StatusServiceBlockingStub statusServiceBlockingStub;
    @Autowired
    private BuildProperties buildProperties;

    @Test
    void testGetReadiness() {
        // Given
        ReadinessResponse expectedReadinessResponse = ReadinessResponse.newBuilder()
                .setStatus("OK")
                .build();

        // When
        ReadinessResponse response = statusServiceBlockingStub.getReadiness(Empty.getDefaultInstance());

        // Then
        assertEquals(response, expectedReadinessResponse);
    }

    @Test
    void testGetVersion() {
        // Given
        VersionResponse expectedVersionResponse = VersionResponse.newBuilder()
                .setArtifact(buildProperties.getArtifact())
                .setName(buildProperties.getName())
                .setGroup(buildProperties.getGroup())
                .setVersion(buildProperties.getVersion())
                .build();

        // When
        VersionResponse response = statusServiceBlockingStub.getVersion(Empty.getDefaultInstance());

        // Then
        assertEquals(response, expectedVersionResponse);
    }
}
