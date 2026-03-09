package com.clientops.api.dto;

import com.clientops.domain.entity.Incident;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class IncidentResponse {

    private Long id;
    private String title;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;

    public IncidentResponse(Long id, String title, String status,
                            LocalDateTime createdAt, LocalDateTime resolvedAt) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
    }

    public static IncidentResponse from(Incident incident) {
        return new IncidentResponse(
                incident.getId(),
                incident.getTitle(),
                incident.getStatus(),
                incident.getCreatedAt(),
                incident.getResolvedAt()
        );
    }

    // getter만 생성
}
