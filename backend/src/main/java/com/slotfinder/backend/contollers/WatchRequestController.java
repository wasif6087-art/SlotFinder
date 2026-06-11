package com.slotfinder.backend.contollers;

import com.slotfinder.backend.models.WatchRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.slotfinder.backend.services.WatchRequestService;


@RestController
public class WatchRequestController {


    private final WatchRequestService watchRequestService;

    public WatchRequestController(WatchRequestService watchRequestService) {
    this.watchRequestService = watchRequestService;
}
    
    @PostMapping("/watchrequests")
    public String createWatchRequest(
        @RequestBody WatchRequest request) {

            return watchRequestService.createWatchRequest(request);
        }
    
}
