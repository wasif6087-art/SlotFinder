package com.slotfinder.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slotfinder.backend.models.WatchRequest;

import java.util.List;

import java.util.Optional;

public interface WatchRequestRepository extends JpaRepository<WatchRequest, Long> {

    List<WatchRequest> findByActiveTrue();

    List<WatchRequest> findByEmailAndActiveTrue(String email);

    Optional<WatchRequest> findByUnsubscribeToken(String unsubscribeToken);

}
