package com.slotfinder.backend.controllers;

import com.slotfinder.backend.models.AppointmentSlot;
import com.slotfinder.backend.models.WatchRequest;
import com.slotfinder.backend.services.WatchRequestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

import jakarta.validation.Valid;


@RestController
public class WatchRequestController {

    private final WatchRequestService watchRequestService;

    public WatchRequestController(WatchRequestService watchRequestService) {
        this.watchRequestService = watchRequestService;
    }

    @PostMapping("/watchrequests")
    public String createWatchRequest(
            @Valid @RequestBody WatchRequest request
    ) {
        return watchRequestService.createWatchRequest(request);
    }

    @GetMapping("/watchrequests/{id}/matches")
    public List<AppointmentSlot> findMatches(
            @PathVariable Long id
    ) throws Exception {
        return watchRequestService.findMatches(id);
    }

    @GetMapping("/unsubscribe/{unsubscribeToken}")
    public String cancelWatchRequest(
            @PathVariable String unsubscribeToken
    ) {
        return watchRequestService.cancelWatchRequest(unsubscribeToken);
    }


}
