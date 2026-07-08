package com.slotfinder.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slotfinder.backend.models.WatchRequest;

public interface WatchRequestRepository extends JpaRepository<WatchRequest, Long> {

}