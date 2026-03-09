package com.clientops.api.dto;

import com.clientops.domain.entity.ApiRequestLog;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ApiRequestLogResponse {
    private Long id;
    private String endpoint;
    private String httpMethod;
    private int statusCode;
    private String apiKey;
    private LocalDateTime requestTime;
    private String clientName; // 고객사 이름 추가

    public ApiRequestLogResponse(ApiRequestLog log) {
        this.id = log.getId();
        this.endpoint = log.getEndpoint();
        this.httpMethod = log.getHttpMethod();
        this.statusCode = log.getStatusCode();
        this.apiKey = log.getApiKey();
        this.requestTime = log.getRequestTime();
        // client가 null일 수 있으므로(인증 실패 시) 방어 코드 작성
        this.clientName = log.getClient() != null ? log.getClient().getName() : "Unknown";
    }
}
