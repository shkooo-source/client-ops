package com.clientops.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "api_request_logs")
public class ApiRequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String endpoint;
    private String httpMethod;
    private int statusCode;

    // 🔥 추가 26.02.04
    @Column(name = "api_key")
    private String apiKey;

    private LocalDateTime requestTime = LocalDateTime.now();

    @ManyToOne
    // 여러 로그 : 하나의 고객사
    // 연관관계는 무조건 단방향부터
    @JoinColumn(name = "client_id")
    private Client client;

    protected ApiRequestLog() {
    }

    public ApiRequestLog(
        String endpoint,
        String httpMethod,
        int statusCode,
        String apiKey,
        Client client
    ) {
        this.endpoint = endpoint;
        this.httpMethod = httpMethod;
        this.statusCode = statusCode;
        this.apiKey = apiKey;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

}