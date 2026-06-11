package com.slotfinder.backend.services;
import com.slotfinder.backend.models.WatchRequest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class WatchRequestService {
    

    public String createWatchRequest(WatchRequest request) {
        return "SERVICE: Watch request received for " + request.getEmail();
    }

}
