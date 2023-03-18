package com.tinkoff.landscape.listener;

import com.tinkoff.landscape.service.SystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationReadyEventListener {

    /**
     * Changes service readiness when ApplicationReadyEvent triggered.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void changeReadinessStatus() {
        SystemService.changeServiceReadiness(true);
    }
}