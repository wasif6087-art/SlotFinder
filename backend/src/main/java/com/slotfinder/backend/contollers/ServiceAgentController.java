package com.slotfinder.backend.contollers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slotfinder.backend.models.ServiceAgent;
import com.slotfinder.backend.services.ServiceAgentService;

@RestController
@RequestMapping("/advisors")
public class ServiceAgentController {

    private final ServiceAgentService serviceAgentService;

    public ServiceAgentController(ServiceAgentService serviceAgentService) {
        this.serviceAgentService = serviceAgentService;
    }

    @GetMapping
    public List<ServiceAgent> getServiceAgents() throws Exception {
        return serviceAgentService.getServiceAgents();
    }
}
