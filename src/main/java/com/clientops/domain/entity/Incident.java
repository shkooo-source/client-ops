package com.clientops.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.Getter;

@Getter
@Entity
@Table(name = "incidents")
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String status; // OPEN, IN_PROGRESS, RESOLVED
    // 상태값을 문자열로 단순화

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime resolvedAt;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    protected Incident() {
    }

    // 🔥 IncidentService에서 쓰려고 하는 생성자
    public Incident(Client client, String title, String status) {
        this.client = client;
        this.title = title;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    // 기존 생성자 (있다면 유지)
    public Incident(String title, Client client) {
        this.title = title;
        this.client = client;
        this.status = "OPEN";
    }

    public void resolve() {
        this.status = "RESOLVED";
        this.resolvedAt = LocalDateTime.now();
    }

}