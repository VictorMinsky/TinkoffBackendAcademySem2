package com.tinkoff.landscape.controller;

import com.tinkoff.landscape.dto.ServiceStatusDTO;
import com.tinkoff.landscape.service.ServicesStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServicesStatusController {
    private final ServicesStatusService servicesStatusService;

    /**
     * Gets statutes for each server defined in application.yml
     *
     * @return Map of services and their {@link ServiceStatusDTO}
     */
    @GetMapping("/statuses")
    public Map<String, List<ServiceStatusDTO>> getStatuses() {
        return servicesStatusService.getStatuses();
    }
}
