package com.clientops.repository;

import com.clientops.domain.entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IncidentRepository
        extends JpaRepository<Incident, Long> {

    boolean existsByTitleAndStatus(String title, String status);

    Optional<Incident> findByIdAndStatus(Long id, String status);

    List<Incident> findAllByStatusOrderByCreatedAtDesc(String status);

    List<Incident> findAllByOrderByCreatedAtDesc();
}
