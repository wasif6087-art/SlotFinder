package com.slotfinder.backend.services;

import com.slotfinder.backend.models.ServiceAgent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
public class ServiceAgentService {

    public List<ServiceAgent> getServiceAgents() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        URI serviceAgentsUri = URI.create(
                "https://api13.comm100.io/booking/serviceAgents?serviceId=b5ae0403bdef420aa87f92b39fa73b7b&siteId=80000203"
        );

        String serviceAgentsResponse = restTemplate.getForObject(
                serviceAgentsUri,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(
                serviceAgentsResponse,
                new TypeReference<List<ServiceAgent>>() {}
                
        );
    }

}