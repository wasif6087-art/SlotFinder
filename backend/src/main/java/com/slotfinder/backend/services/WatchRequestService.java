package com.slotfinder.backend.services;
import com.slotfinder.backend.models.WatchRequest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WatchRequestService {

    private List<WatchRequest> watchRequests = new ArrayList<>();
    private Long nextId = 1L;
    

    public String createWatchRequest(WatchRequest request) {

        request.setId(nextId);
        nextId++;

        request.setActive(true);

        request.setCreatedAt(LocalDateTime.now());


        watchRequests.add(request);

        return "SERVICE: Watch request received for " + request.getEmail();
    }

    public List<WatchRequest> getAllWatchRequests() {
        return watchRequests;
    } 

    public String cancelWatchRequest(Long id) {
        for (WatchRequest request : watchRequests) {
            if (request.getId().equals(id)) {
                request.setActive(false);
                return "Watch request has been cacelled for " + request.getEmail();
            } 
        }

    
        return "No watch request found";
    }

}
